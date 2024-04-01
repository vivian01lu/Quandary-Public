package ast;

import java.util.HashMap;

public class ReturnStmt extends Stmt {
    private Expr expr;

    public ReturnStmt(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

  public Expr getExpr() {
      return expr;
  }
    public Long executeReturnStatement(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        // Extract information from the return statement
        Expr expr = this.getExpr();
        Object value = expr.execute(variableMap, funcDefMap);
        returnStatus.setStatus(true);
        return (Long)value;
    }

    // Object executeReturnStatement(ReturnStmt returnStmt,FuncDef func,HashMap<String, Long> insideMap) {
    //     // Evaluate the expression
    //     Object value = evaluate(returnStmt.getExpr(),func,insideMap);
    //     returnFlag = true;
    //     isRet = true;
    //     return value;
    // }
}
