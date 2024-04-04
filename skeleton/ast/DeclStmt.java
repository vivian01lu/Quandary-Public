package ast;

import java.util.HashMap;
import java.util.Map;

public class DeclStmt extends Stmt {
    private VarDecl varDecl;
    private Expr expr;

    public DeclStmt(VarDecl varDecl, Expr expr, Location loc) {
        super(loc);
        this.varDecl = varDecl;
        this.expr = expr;
    }

    public VarDecl getVarDecl() {
        return varDecl;
    }

    public Expr getExpr() {
        return expr;
    }

      public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        // Extract information from the declaration statement
        String varName = this.getVarDecl().getIdent().toString();
        Expr expr = this.getExpr();
        QVal value = expr.execute(variableMap, funcDefMap);
        //if the stmt is "int a  = arg" then put "a"-"18" to the hashmap
        variableMap.put(varName,value);
        return null;
      }
}
