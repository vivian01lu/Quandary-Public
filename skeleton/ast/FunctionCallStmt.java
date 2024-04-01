package ast;

import java.util.HashMap;

public class FunctionCallStmt extends Stmt{
    final FuncCallExpr funcCallExpr;

    public FunctionCallStmt(FuncCallExpr funcCallExpr, Location loc) {
        super(loc);
        this.funcCallExpr = funcCallExpr;
    }
    public FuncCallExpr getFCE() {
        return funcCallExpr;
    }

    public Object executeFucCallStmt(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        return funcCallExpr.execute(variableMap, funcDefMap);
    }
}
