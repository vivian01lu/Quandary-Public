package interpreter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.concurrent.Semaphore;

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
    //create a map to store function name and their definition
    public HashMap<String, FuncDef> funcDefMap = new HashMap<String, FuncDef>();
   
    static public Interpreter interpreter;

    public static Semaphore semaphore = new Semaphore(1);

    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public static void main(String[] args) {
                        // args = new String[2];
                        // args[0] = "examples/steps6.q";
                        // args[1] = "1";

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
        String returnValueAsString = interpreter.executeRoot(astRoot, new QInt(quandaryArg)).toString();
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

    Object executeRoot(Program astRoot, QVal arg) {
      
        return executeProgram(astRoot,arg);
    }

    Object executeProgram(Program program,QVal arg) {
    
        return executeFunctionDefList(program.getFuncDefList(),arg);
    }

    Object executeFunctionDefList(FuncDefList funcDefList,QVal arg) {
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
        //use a list to save all args in the main function
        List<QVal> argsList = new ArrayList<>();
        argsList.add(arg);//for now it
  
        QVal result = mainFunc.execute(funcDefMap, argsList);

      return result;
    }

	public static void fatalError(String message, int processReturnCode) {
        System.out.println(message);
        System.exit(processReturnCode);
	}

    static  public QInt setLeft (Expr e1, Expr e2,HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        semaphore.acquireUninterruptibly();
        QRef ref = (QRef)e1.execute(variableMap, funcDefMap);
        ref.referent.left = e2.execute(variableMap, funcDefMap);
        semaphore.release();
        return new QInt(1);
    }
    static public QInt setRight (Expr e1, Expr e2,HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        semaphore.acquireUninterruptibly();
        QRef ref = (QRef)e1.execute(variableMap, funcDefMap);
        ref.referent.right = e2.execute(variableMap, funcDefMap);
        semaphore.release();
        return new QInt(1);
    }

    static  public QInt isNil(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap ){
        QVal qVal = e.execute(variableMap, funcDefMap);
        if(qVal instanceof QRef){
            QRef ref = (QRef)qVal;
            if(ref.referent == null){
                return new QInt(1);
            }
        }
        return new QInt(0);
    }
    static public QVal left(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        QRef ref = (QRef)e.execute(variableMap, funcDefMap);
        QVal leftQVal = ref.referent.getLeft();
        return leftQVal;
    }
    static public QVal right(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        QRef ref = (QRef)e.execute(variableMap, funcDefMap);
        QVal rightQVal = ref.referent.getRight();
        return rightQVal;
    }

    static public QInt randomInt(Expr e,HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        int range;
        if (e instanceof ConstExpr){
            ConstExpr constExpr = (ConstExpr)e;
            Long val = constExpr.getValue();
            range = val.intValue();
            //System.out.println(val);
            
            Random rand = new Random();
            int randomNum = rand.nextInt(Integer.parseInt(val.toString() ));
            System.out.println(randomNum);
            return new QInt(randomNum);//return a random number
        } else if (e instanceof IdentExpr){
            IdentExpr identExpr = (IdentExpr)e;
            String ident = identExpr.getValue();
            QVal qVal = variableMap.get(ident);
            if(qVal instanceof QInt){
                QInt qInt = (QInt)qVal;
                range = qInt.getValue().intValue();
                Random rand = new Random();
                int randomNum = rand.nextInt(Integer.parseInt(qInt.getValue().toString() ));
                return new QInt(randomNum);//return a random number
            }


        }
        return new QInt(0);
    }

    static public QInt isAtom(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        QVal qVal = e.execute(variableMap, funcDefMap);
        if(qVal instanceof QInt){
            return new QInt(1);
        }else if(qVal instanceof QRef){
            return isNil(e,variableMap,funcDefMap);
        }
        return new QInt(0);
    }

    static public QInt acq(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        //System.out.println(variableMap);
        QRef q1 = (QRef)e.execute(variableMap, funcDefMap);
        QObj obj = q1.referent;
        obj.acq();
        try {
            //Thread.sleep(30000);
        }
        catch (Exception ex) {

        }
        return new QInt(1);
    }
    static public QInt rel(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        QRef q2 = (QRef)e.execute(variableMap, funcDefMap);
        QObj obj = q2.referent;
        obj.rel();
        try {
            //Thread.sleep(30000);
        }
        catch (Exception ex) {

        }
        return new QInt(1);
    }
}
