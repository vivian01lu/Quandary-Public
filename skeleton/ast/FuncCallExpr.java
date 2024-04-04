package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
                return  random();
              
            }else if (this.getFuncName().equals("left")) {
                Expr e  = arguments.get(0);
                return left(e,variableMap,funcDefMap);
               
            }else if(this.getFuncName().equals("right")){
                Expr e  = arguments.get(0);
               return right(e, variableMap, funcDefMap);
            }
            else if(this.getFuncName().equals("isNil")){
                Expr e  = arguments.get(0);
                return isNil(e,variableMap,funcDefMap);
              
            }else if (this.getFuncName().equals("setLeft")) {
                //setLeft(e1,e2) sets the left field of the reference e1 to the value of e2
                Expr e1 = arguments.get(0);
                Expr e2 = arguments.get(1);
                return setLeft(e1, e2 ,variableMap ,funcDefMap);
              
            }else if(this.getFuncName().equals("setRight")){
                //setRight(e1,e2) sets the right field of the reference e1 to the value of e2
                Expr e1 = arguments.get(0);
                Expr e2 = arguments.get(1);
                return setRight(e1, e2, variableMap, funcDefMap);
              
            }else if (this.getFuncName().equals("isAtom")) {
                //isAtom(e) returns 1 if e is an atom, and 0 otherwise
                Expr e = arguments.get(0);
                return isAtom(e, variableMap, funcDefMap);
            }
            
            return curFucDef.execute(funcDefMap, paramlist);  
    }

    QInt setLeft (Expr e1, Expr e2,HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        QRef ref = (QRef)e1.execute(variableMap, funcDefMap);
        ref.referent.left = e2.execute(variableMap, funcDefMap);
        return new QInt(1);
    }
    QInt setRight (Expr e1, Expr e2,HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        QRef ref = (QRef)e1.execute(variableMap, funcDefMap);
        ref.referent.right = e2.execute(variableMap, funcDefMap);
        return new QInt(1);
    }

    QInt isNil(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap ){
        QVal qVal = e.execute(variableMap, funcDefMap);
        if(qVal instanceof QInt){
            return new QInt(1);
        }else if(qVal instanceof QRef){
            QRef ref = (QRef)qVal;
            if(ref.referent == null){
                return new QInt(1);
            }
        }
        return new QInt(0);
    }
    QVal left(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        QRef ref = (QRef)e.execute(variableMap, funcDefMap);
        QVal leftQVal = ref.referent.getLeft();
        return leftQVal;
    }
    QVal right(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        QRef ref = (QRef)e.execute(variableMap, funcDefMap);
        QVal rightQVal = ref.referent.getRight();
        return rightQVal;
    }

    QInt random(){
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        return new QInt(randomNum);//return a random number
    }

    QInt isAtom(Expr e, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap){
        QVal qVal = e.execute(variableMap, funcDefMap);
        if(qVal instanceof QInt){
            return new QInt(1);
        }else if(qVal instanceof QRef){
            return isNil(e,variableMap,funcDefMap);
        }
        return new QInt(0);
    }

}
    