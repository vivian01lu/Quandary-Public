package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import interpreter.Interpreter;

/**
 * FuncCallExpr
 */
public class FuncCallExpr extends Expr {
    final   String  funcName;
    final   List<Expr>  args;
   

    public FuncCallExpr(String funcName, List<Expr> args,Location loc) {
        super(loc);
        this.funcName = funcName;
        this.args = args;
    
    }
    
    public String getFuncName() {
        return funcName;
    }

    public List<Expr> getArgu() {
        return args;
    }
    public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
            String funcName = this.getFuncName();
            FuncDef curFucDef = funcDefMap.get(funcName);

            List<Expr> arguments = this.getArgu();//this returns "arg"
            //this list saves the value of the actual parameters
            List<QVal> paramlist = new ArrayList<>();
            for(Expr e : arguments){
                QVal val = e.execute(variableMap, funcDefMap);
                //Long val = (Long) insideMap.get(e.toString());
                paramlist.add(val);
            }

            //check for quandary built-in functions
            if (this.getFuncName().equals("randomInt")) {
                return Interpreter.random();
              
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
                return Interpreter.setLeft(e1, e2 ,variableMap ,funcDefMap);
              
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
            
            return curFucDef.execute(funcDefMap, paramlist);  
    }

   
}
    