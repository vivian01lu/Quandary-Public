package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import interpreter.Interpreter;

public class FunctionCallStmt extends Stmt{
    final List<Expr> arguments;
    final String IDENT;
    final Location loc;
 

    public FunctionCallStmt(String s, List<Expr> arguments, Location loc) {
        super(loc);
        this.arguments = arguments;
        this.IDENT = s;
        this.loc = loc;
    }
    //get the ExprList
    public List<Expr> getExprList() {
        return arguments;
    }
    //get the name of this function
    public String getFuncName() {
        return this.IDENT;
    }
    //getLoc
    public Location getLoc() {
        return loc;
    }
  
    public QVal executeFucCallStmt(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
             if (this.getFuncName().equals("randomInt")) {
                 Expr e  = arguments.get(0);
                return Interpreter.randomInt(e, variableMap, funcDefMap);
              
            }else if (this.getFuncName().equals("left")) {
                Expr e  = arguments.get(0);
                return Interpreter.left(e, variableMap, funcDefMap);
               
            }else if(this.getFuncName().equals("right")){
                Expr e  = arguments.get(0);
               return Interpreter.right(e, variableMap, funcDefMap);
            }
            else if(this.getFuncName().equals("isNil")){
                Expr e  = arguments.get(0);
                return Interpreter.isNil(e, variableMap, funcDefMap);
              
            }else if (this.getFuncName().equals("setLeft")) {
                //setLeft(e1,e2) sets the left field of the reference e1 to the value of e2
                Expr e1 = arguments.get(0);
                Expr e2 = arguments.get(1);
                return Interpreter.setLeft(e1, e2, variableMap, funcDefMap);
              
            }else if(this.getFuncName().equals("setRight")){
                //setRight(e1,e2) sets the right field of the reference e1 to the value of e2
                Expr e1 = arguments.get(0);
                Expr e2 = arguments.get(1);
                return Interpreter.setRight(e1, e2, variableMap, funcDefMap);
              
            }else if (this.getFuncName().equals("isAtom")) {
                //isAtom(e) returns 1 if e is an atom, and 0 otherwise
                Expr e = arguments.get(0);
                return Interpreter.isAtom(e, variableMap, funcDefMap);
            }

            FuncCallExpr callExpr = new FuncCallExpr(this.getFuncName(), this.getExprList(), this.getLoc());
            callExpr.execute(variableMap, funcDefMap);
          
        return null;
    }
}
