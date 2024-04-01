package interpreter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import parser.ParserWrapper;
import ast.*;

public class Interpreter {

    // Process return codes
    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_PARSING_ERROR = 1;
    public static final int EXIT_STATIC_CHECKING_ERROR = 2;
    public static final int EXIT_DYNAMIC_TYPE_ERROR = 3;
    public static final int EXIT_NIL_REF_ERROR = 4;
    public static final int EXIT_QUANDARY_HEAP_OUT_OF_MEMORY_ERROR = 5;
    public static final int EXIT_DATA_RACE_ERROR = 6;
    public static final int EXIT_NONDETERMINISM_ERROR = 7;
    //create a map to store program variables name and their values
    private HashMap<String, Long> map = new HashMap<String, Long>();
    //create a map to store function name and their definition
    public HashMap<String, FuncDef> funcDefMap = new HashMap<String, FuncDef>();
   
    static public Interpreter interpreter;

    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public static void main(String[] args) {
            //   args = new String[2];
            //   args[0] = "examples/precedence3.q";
            //   args[1] = "42";

        String gcType = "NoGC"; // default for skeleton, which only supports NoGC
        long heapBytes = 1 << 14;
        int i = 0;
        String filename;
        long quandaryArg;
        try {
            for (; i < args.length; i++) {
                String arg = args[i];
                if (arg.startsWith("-")) {
                    if (arg.equals("-gc")) {
                        gcType = args[i + 1];
                        i++;
                    } else if (arg.equals("-heapsize")) {
                        heapBytes = Long.valueOf(args[i + 1]);
                        i++;
                    } else {
                        throw new RuntimeException("Unexpected option " + arg);
                    }
                } else {
                    if (i != args.length - 2) {
                        throw new RuntimeException("Unexpected number of arguments");
                    }
                    break;
                }
            }
            filename = args[i];
            quandaryArg = Long.valueOf(args[i + 1]);
        } catch (Exception ex) {
            System.out.println("Expected format: quandary [OPTIONS] QUANDARY_PROGRAM_FILE INTEGER_ARGUMENT");
            System.out.println("Options:");
            System.out.println("  -gc (MarkSweep|Explicit|NoGC)");
            System.out.println("  -heapsize BYTES");
            System.out.println("BYTES must be a multiple of the word size (8)");
            return;
        }

        Program astRoot = null;
        Reader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            astRoot = ParserWrapper.parse(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
            Interpreter.fatalError("Uncaught parsing error: " + ex, Interpreter.EXIT_PARSING_ERROR);
        }
        //astRoot.println(System.out);
        interpreter = new Interpreter(astRoot);
        interpreter.initMemoryManager(gcType, heapBytes);
        String returnValueAsString = interpreter.executeRoot(astRoot, quandaryArg).toString();
        System.out.println("Interpreter returned " + returnValueAsString);
    }

    final Program astRoot;
    final Random random;

    private Interpreter(Program astRoot) {
        this.astRoot = astRoot;
        this.random = new Random();
    }

    void initMemoryManager(String gcType, long heapBytes) {
        if (gcType.equals("Explicit")) {
            throw new RuntimeException("Explicit not implemented");            
        } else if (gcType.equals("MarkSweep")) {
            throw new RuntimeException("MarkSweep not implemented");            
        } else if (gcType.equals("RefCount")) {
            throw new RuntimeException("RefCount not implemented");            
        } else if (gcType.equals("NoGC")) {
            // Nothing to do
        }
    }

    Object executeRoot(Program astRoot, long arg) {
      
        return executeProgram(astRoot,arg);
    }

    Object executeProgram(Program program,long arg) {
    
        return executeFunctionDefList(program.getFuncDefList(),arg);
    }

    Object executeFunctionDefList(FuncDefList funcDefList,long arg) {
        //add funcDef to the hashmap funcDefMap
        List<FuncDef> funcDefs = funcDefList.getFL();
        FuncDef mainFunc = null;
        for (FuncDef funcDef : funcDefs) {
            //save all the other funcDefMap' identifier and function definition
            funcDefMap.put(funcDef.getVarDecl().getIdent(), funcDef);
            if(funcDef.getVarDecl().getIdent().equals("main")){
                mainFunc = funcDef;
            }

        }
        //use a list to args in the main function
        List<Long> argsList = new ArrayList<>();
        argsList.add(arg);
  
        Long result = mainFunc.execute(funcDefMap, argsList);
    
       return result;
    }



    // Object executeIfStatement(IfStmt ifStmt, FuncDef func, HashMap<String, Long> insideMap) {
    //     // Evaluate the condition
    //     Object value = null;
    //     Cond c =ifStmt.getCond();
    //     boolean condition = evaluateCond(c,func,insideMap);
    //     if (condition){
    //         value = executeStatement(ifStmt.getStmt(), func, insideMap);
    //     }
    //     return value;
    // }

    // Object executeIfElseStatement(IfElseStmt ifElseStmt, FuncDef func, HashMap<String, Long> insideMap) {
    //     // Evaluate the condition
    //     Object value = null;
    //     boolean condition = evaluateCond(ifElseStmt.getCond(),func,insideMap);
    //     if (condition){
    //         value = executeStatement(ifElseStmt.getStmt1(), func, insideMap);
    //     } else {
    //         value = executeStatement(ifElseStmt.getStmt2(), func, insideMap);
    //     }
    //     return value;
    // }

    // Object executeWhileStatement(WhileStmt whileStmt,FuncDef func,HashMap<String, Long> insideMap) {
    //     Object value = null;
    //     boolean condition = evaluateCond(whileStmt.getCond(),func,insideMap);
    //     // Execute the while loop based on the condition
    //     while (condition) {
    //         value =  executeStatement(whileStmt.getStmt(), func,insideMap);
    //         condition = evaluateCond(whileStmt.getCond(),func,insideMap);
    //     }
    //     return value;
    // }

    // Object executePrintStatement(PrintStmt printStmt,FuncDef func,HashMap<String, Long> insideMap) {
    //     // Evaluate the expression
    //     isRet = true;
    //     Object value = evaluate(printStmt.getExpr(),func,   insideMap);
    //     isRet = false;
    //     // Print the result
    //     System.out.println(value);
    //     return value;
    // }
    
    // Object executeReturnStatement(ReturnStmt returnStmt,FuncDef func,HashMap<String, Long> insideMap) {
    //     // Evaluate the expression
    //     Object value = evaluate(returnStmt.getExpr(),func,insideMap);
    //     returnFlag = true;
    //     isRet = true;
    //     return value;
    // }

    // boolean evaluateCond(Cond cond,FuncDef func,HashMap<String, Long> insideMap) {
    //     if (cond instanceof BinaryCond) {
    //         boolean result = false;
    //         BinaryCond binaryCond = (BinaryCond)cond;
    //         switch (binaryCond.getOperator()) {
    //             case 4:
    //                 //LE
    //                 Expr e1 = binaryCond.getExpr1();
                   
    //                 Expr e2 = binaryCond.getExpr2();
    //                 Long leftVal = (Long)evaluate(e1,func ,insideMap );
    //                 Long rightVal = (Long)evaluate(e2,func ,insideMap );
    //                 result =leftVal  <= rightVal;
    //                 break;
    //             case 5:
    //                 //GE
    //                 result =(Long)evaluate(binaryCond.getExpr1(),func, insideMap) >= (Long)evaluate(binaryCond.getExpr2(),func,insideMap);
    //                 break;
    //             case 6:
    //                 //EQ
    //                 result = evaluate(binaryCond.getExpr1(),func,insideMap).equals(evaluate(binaryCond.getExpr2(),func,insideMap));
    //                 break;
    //             case 7:
    //                 //NE
    //                 result = !evaluate(binaryCond.getExpr1(),func,insideMap).equals(evaluate(binaryCond.getExpr2(),func,insideMap));
    //                 break;
    //             case 8:
    //                 //LT
    //                 result = (Long)evaluate(binaryCond.getExpr1(),func,insideMap) < (Long)evaluate(binaryCond.getExpr2(),func,insideMap);
    //                 break;
    //             case 9:
    //                 //GT
    //                 result = (Long)evaluate(binaryCond.getExpr1(),func,insideMap) > (Long)evaluate(binaryCond.getExpr2(),func,insideMap);
    //                 break;
    //             case 10:
    //                 //AND
    //                 Boolean left = evaluateCond((Cond) binaryCond.getExpr1(), func,insideMap);
    //                 Boolean right = evaluateCond((Cond) binaryCond.getExpr2(), func,insideMap);
    //                 result = (left != null && right != null) && left && right;

    //                 break;
    //             case 11:
    //                 //OR
    //                 result = (boolean) evaluateCond((Cond)binaryCond.getExpr1(),func,insideMap) || (boolean) evaluateCond((Cond)binaryCond.getExpr2(),func,insideMap);
    //                 break;
    //             case 0:
    //                 //NOT
    //                 result = !(boolean) evaluateCond((Cond)binaryCond.getExpr1(),func,insideMap);
    //                 break;
    //             default:
    //                 break;
    //         }
    //         return result;
    //     } 
    //     // else if (cond instanceof UnaryCond) {
    //     //     UnaryCond unaryCond = (UnaryCond)cond;
    //     //     return !(boolean) evaluateCond((Cond)unaryCond.getExpr(),func,insideMap);}
        
    //     else {
    //         throw new RuntimeException("Unhandled Cond type");
    //     }
    // }

   

    // Object evaluate(Expr expr, FuncDef func, HashMap<String, Long> insideMap ) {
    //     if (expr instanceof ConstExpr) {
    //         return ((ConstExpr)expr).getValue();
    //     } else if (expr instanceof BinaryExpr) {
    //         BinaryExpr binaryExpr = (BinaryExpr)expr;
    //         Object left = evaluate(binaryExpr.getLeftExpr(), func, insideMap);
    //         Object right = evaluate(binaryExpr.getRightExpr(), func, insideMap);
    
    //         if (left == null || right == null) {
    //             return null;
    //         }
    
    //         switch (binaryExpr.getOperator()) {
    //             case BinaryExpr.PLUS:
    //                 return (Long) left + (Long) right;
    //             case BinaryExpr.MINUS:
    //                 return (Long) left - (Long) right;
    //             case BinaryExpr.MULTIPLY:
    //                 return (Long) left * (Long) right;
    //             default:
    //                 throw new RuntimeException("Unhandled operator");
    //         }
    //     }else if(expr instanceof UnaryMinusExpr) {
    //         Expr child = ((UnaryMinusExpr)expr).getExpr();
    //         long value = (long) evaluate(child,func,insideMap);
    //         long newValue = -value;
    //         return newValue;
    //     }else if(expr instanceof IdentExpr) {
    //         String ident = ((IdentExpr)expr).getValue();
    //         return insideMap.get(ident);
    //     }else if(expr instanceof FuncCallExpr) {
    //         FuncCallExpr funcCallExpr = (FuncCallExpr)expr;
    //         //the executing function name
    //         String funcName = funcCallExpr.getFuncName();
    //         FuncDef curFucDef = funcDefMap.get(funcName);

    //         List<Expr> arguments = funcCallExpr.getArgu();//this returns "arg"
    //         //this list saves the value of the actual parameters
    //         List<Long> paramlist = new ArrayList<>();
    //         for(Expr e : arguments){
    //              Long val =(Long)evaluate(e,curFucDef,insideMap);
    //             //Long val = (Long) insideMap.get(e.toString());
    //             paramlist.add(val);
    //         }

    //         if (((FuncCallExpr)expr).getFuncName().equals("randomInt")) {
    //             Random random = new Random();
    //             return (long)random.nextInt((paramlist.get(0)).intValue());
    //         }
    //         return executeFuncDef(curFucDef, paramlist);           
    //     }else {
    //         throw new RuntimeException("Unhandled Expr type");
    //     }
        
    // }
	public static void fatalError(String message, int processReturnCode) {
        System.out.println(message);
        System.exit(processReturnCode);
	}
}
