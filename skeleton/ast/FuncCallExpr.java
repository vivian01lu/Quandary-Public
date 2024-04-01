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
    public Long execute(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap) {
            String funcName = this.getFuncName();
            FuncDef curFucDef = funcDefMap.get(funcName);

            List<Expr> arguments = this.getArgu();//this returns "arg"
            //this list saves the value of the actual parameters
            List<Long> paramlist = new ArrayList<>();
            for(Expr e : arguments){
                 Long val = e.execute(variableMap, funcDefMap);
                //Long val = (Long) insideMap.get(e.toString());
                paramlist.add(val);
            }

            if (this.getFuncName().equals("randomInt")) {
                Random random = new Random();
                return (long)random.nextInt((paramlist.get(0)).intValue());
            }

        return curFucDef.execute(funcDefMap, paramlist);    
    }
}
    