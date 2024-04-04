package ast;

import java.util.HashMap;

public class PrintStmt extends Stmt{
    private final Expr expr;

    public PrintStmt(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public void executePrintStatement(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {

        // Extract information from the print statement
        Expr expr = this.getExpr();
        Object value = expr.execute(variableMap, funcDefMap);
        System.out.println(value);
       
    }

}
