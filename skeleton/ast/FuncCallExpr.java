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
                Random rand = new Random();
                int randomNum = rand.nextInt(100);
                return new QInt(randomNum);//return a random number
            }else if (this.getFuncName().equals("left")) {
                Expr e  = arguments.get(0);
                QRef ref = (QRef)e.execute(variableMap, funcDefMap);
                QVal leftQVal = ref.referent.getLeft();
                return leftQVal;
            }
        return curFucDef.execute(funcDefMap, paramlist);    
    }

   
}
    