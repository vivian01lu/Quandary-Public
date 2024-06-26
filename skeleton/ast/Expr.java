package ast;

import java.util.HashMap;

public class Expr extends ASTNode {

    Expr(Location loc) {
        super(loc);
    }

    public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        QVal result = null;

        if (this instanceof ConstExpr) {
            ConstExpr constExpr = (ConstExpr) this;
            result = constExpr.execute(variableMap, funcDefMap);

        } else if (this instanceof IdentExpr) {
            IdentExpr idExpr = (IdentExpr) this;
            result = idExpr.execute(variableMap, funcDefMap);

        } else if (this instanceof TypeCastExpr) {
            TypeCastExpr castExpr = (TypeCastExpr) this;
            result = castExpr.execute(variableMap, funcDefMap);

        }
        else if (this instanceof UnaryMinusExpr) {
            UnaryMinusExpr unaryExpr = (UnaryMinusExpr) this;
            result = unaryExpr.execute(variableMap, funcDefMap);

        } else if (this instanceof BinaryExpr) {
            BinaryExpr binaryExpr = (BinaryExpr) this;
            result = binaryExpr.execute(variableMap, funcDefMap);

        } else if (this instanceof FuncCallExpr) {
            FuncCallExpr callExpr = (FuncCallExpr) this;
            result = callExpr.execute(variableMap, funcDefMap);
        }else if(this instanceof NilExpr){
            NilExpr nilExpr = (NilExpr) this;
            result = nilExpr.execute(variableMap, funcDefMap);
        }else if(this instanceof ConcurExpr){
            ConcurExpr concurExpr = (ConcurExpr) this;
            result = concurExpr.executeConcurrency(variableMap, funcDefMap);
        }
        return result;
    
    }
}
