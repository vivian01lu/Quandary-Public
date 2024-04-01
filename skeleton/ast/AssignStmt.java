package ast;

import java.util.HashMap;
import java.util.Map;

public class AssignStmt extends Stmt {
    private String ident;
    private Expr expr;

    public AssignStmt(String ident, Expr expr, Location loc) {
        super(loc);
        this.ident = ident;
        this.expr = expr;
    }
     public Expr getExpr() {
         return expr;
     }
     public String getIdent() {
         return ident;
     }

     Object executeAssignmentStatement(HashMap<String, Long> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        // Extract information from the assignment statement
        String ident = this.getIdent();
        Expr expr = this.getExpr();
        Object value = expr.execute(variableMap, funcDefMap);

        variableMap.put(ident, (Long)value);
    
        return value;
     }
}