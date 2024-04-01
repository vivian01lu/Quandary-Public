package ast;

import java.util.HashMap;

public class ConstExpr extends Expr {

    final Object value;

    public ConstExpr(long value, Location loc) {
        super(loc);
        this.value = value;
    }
    //empty constructor for null
    public ConstExpr(Location loc) {
        super(loc);
        this.value = null;
    }

    public Object getValue() {
        return value;
    }

    public Long execute(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap) {
        return (Long) value;
    }
}
