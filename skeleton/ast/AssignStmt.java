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

    public void executeAssignmentStatement(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        // Extract information from the assignment statement
        String ident = this.getIdent();
        Expr expr = this.getExpr();
//        System.out.println("assign statement " + ident);
        QVal value = expr.execute(variableMap, funcDefMap);

        variableMap.put(ident, value);
    
       
     }
}