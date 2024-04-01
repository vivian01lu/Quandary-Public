package ast;

import java.util.HashMap;
import java.util.List;

public class BlockStmt extends Stmt {
    private  final List<Stmt> sl;

    public BlockStmt(List<Stmt> sl, Location loc) {
        super(loc);
        this.sl = sl;
    }

    public List<Stmt> getSl() {
        return sl;
    }

    Object executeBlockStatement(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        Long result = null;
        for (int i = 0; i < sl.size() && !returnStatus.isStatus(); i++) {
            result = sl.get(i).execute(variableMap, funcDefMap, returnStatus);
        }
        return result;
    }
}
