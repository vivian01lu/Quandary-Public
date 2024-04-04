package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StmtList extends ASTNode {

    private final List<Stmt> sl;

    private final Location loc;
    public StmtList(List<Stmt> sl, Location loc) {
        super(loc);
        this.sl = sl;
        this.loc = loc;
    }

    public List<Stmt> getStmtList() {
        return sl;
    }
    public Location getLocation() {
        return this.loc;
    }

    public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        QVal result = null;
        //as long as returnStatus.isStatus() == false we will keep executing the statements
        for (int i = 0; i < sl.size() && !returnStatus.isStatus(); i++) {
            result = sl.get(i).execute(variableMap, funcDefMap, returnStatus);
        }
        return result;    
    } 
}