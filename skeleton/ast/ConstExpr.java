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

    public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        QVal result = null;
        if (((ConstExpr)this).getValue() instanceof Long){
            result = new QInt((Long)((ConstExpr)this).getValue());
        }else if (((ConstExpr)this).getValue() == null){
            result =new QRef(null);
        }
        return result;
    }
}
