package ast;

import java.util.HashMap;
import java.util.Map;

public class IfElseStmt extends Stmt {
    final Cond cond;
    final Stmt stmt1;
    final Stmt stmt2;

    public IfElseStmt(Cond cond, Stmt stmt1, Stmt stmt2, Location loc) {
        super(loc);
        this.cond = cond;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    public Cond getCond() {
        return cond;
    }
    public Stmt getStmt1() {
        return stmt1;
    }
    public Stmt getStmt2() {
        return stmt2;
    }
    public Long executeIfElseStatement(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        Long result = null;

        boolean conditionValueResult = cond.execute(variableMap, funcDefMap);

        if(conditionValueResult){
            result = stmt1.execute(variableMap, funcDefMap, returnStatus);
        } else {
            result = stmt2.execute(variableMap, funcDefMap, returnStatus);
        }

        return result;
    }
}
