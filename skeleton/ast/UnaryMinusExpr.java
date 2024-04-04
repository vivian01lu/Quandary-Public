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

      public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        QInt val = (QInt)(expr.execute(variableMap, funcDefMap));
        return new QInt(-val.getValue());
      }
}
