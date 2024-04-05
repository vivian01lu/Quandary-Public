package ast;

import java.util.HashMap;

public class WhileStmt extends Stmt {
    final Cond cond;
    final Stmt stmt;

    public WhileStmt(Cond cond, Stmt stmt, Location loc) {
        super(loc);
        this.cond = cond;
        this.stmt = stmt;
    }

    public Cond getCond() {
        return cond;
    }
    public Stmt getStmt() {
        return stmt;
    }

     public QVal executeWhileStmt(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        QVal result = null;
        // return 0 indicates false;
        // return 1 indicates true;
        boolean conditionValueResult = cond.execute(variableMap, funcDefMap);

        //System.out.println("Run IfStmt, cond = " + cond.toString());
        //System.out.println("Run IfStmt, conditionValueResult = " + conditionValueResult);

        while(conditionValueResult){
            //System.out.println("Run IfStmt, isReturn = " + returnStatus.isStatus());

            result = stmt.execute(variableMap, funcDefMap, returnStatus);
            if(returnStatus.isStatus() == false){
                //System.out.println("Run IfStmt, isReturn = " + returnStatus.isStatus() + " ,result = " + result + " after stmt.execute()");
                conditionValueResult = cond.execute(variableMap, funcDefMap);
            }else{
                return result;
            }
        }

        return result;
     }
}
