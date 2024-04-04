package ast;

import java.util.HashMap;
import java.util.Map;

public class IfStmt extends Stmt {
    final Cond cond;
    final Stmt stmt;

    public IfStmt(Cond cond, Stmt stmt, Location loc) {
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

     public QVal executeIfStatement(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        QVal result = null;
        boolean conditionValueResult = cond.execute(variableMap, funcDefMap);

        if(conditionValueResult){
            result = stmt.execute(variableMap, funcDefMap, returnStatus);
        }
        return result;
     }
}