package ast;
import java.util.HashMap;
import java.util.List;

import interpreter.Interpreter;


public class FuncDefList extends ASTNode {

    private static final String mainFunction = "main";
    
    private final List<FuncDef> functionDefs;

    public FuncDefList( List<FuncDef> functionDefs, Location loc) {
        super(loc);
        this.functionDefs = functionDefs;
        
    }

    public List<FuncDef> getFL() {
        return functionDefs;
    }
}
