package ast;

import java.util.HashMap;

public class TypeCastExpr extends Expr {

    final Type.TYPE t;
    final Expr e;

    public TypeCastExpr(Type.TYPE t, Expr e, Location loc) {
        super(loc);
        this.t = t;
        this.e = e;
    }

    public Type.TYPE getType() {
        return t;
    }

    public Expr getExpr() {
        return e;
    }

     public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        if (this.getType().equals(Type.TYPE.INT)) {
            return (QInt)this.getExpr().execute(variableMap, funcDefMap);
        } else if (this.getType().equals(Type.TYPE.REF)) {
            return (QRef)this.getExpr().execute(variableMap, funcDefMap);
        }
        else return this.execute(variableMap, funcDefMap);
     }
}