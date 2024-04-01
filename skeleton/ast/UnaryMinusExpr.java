package ast;

import java.util.HashMap;

public class UnaryMinusExpr extends Expr {

    final Expr expr;

    public UnaryMinusExpr(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }
    @Override
    public String toString() {
        return "-(" + expr + ")";
    }

      public Long execute(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap) {
        return -expr.execute(variableMap, funcDefMap);
      }
}
