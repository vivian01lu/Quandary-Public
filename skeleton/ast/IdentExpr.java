package ast;

import java.util.HashMap;

public class IdentExpr extends Expr {

    final String i;

    public IdentExpr(String i, Location loc) {
        super(loc);
        this.i = i;
    }

    public String getValue() {
        return i;
    }

    @Override
    public String toString() {
        return i;
    }

      public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        return variableMap.get(i);
      }
}