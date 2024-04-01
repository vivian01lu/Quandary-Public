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

    // public FuncDef getMain() {
    //     if (functionDefs != null && functionDefs.size() > 0) {
    //         for (int i = 0; i < functionDefs.size(); i++) {
    //             if (functionDefs.get(i).getVarDecl().getIdent().equals(mainFunction)) {
    //                 return functionDefs.get(i);
    //             }
    //         }
    //     }
    //     return null;
    // }

}
