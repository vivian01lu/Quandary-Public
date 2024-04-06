package ast;
import java.util.HashMap;

public class NilExpr extends Expr{
    //constructor for NilExpr
    public NilExpr(Location loc) {
        super(loc);
    }
    //toString()
    public String toString() {
        return "nil";
    }
    //execute
    public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        return new QRef(null);
    }
}
