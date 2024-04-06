package ast;

import java.util.HashMap;


public abstract class Stmt extends ASTNode {

    Stmt(Location loc) {
        super(loc);
    }
   
    public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap, ReturnStatus returnStatus) {
        QVal result = null;

        if (this instanceof DeclStmt) {
            DeclStmt declStmt = (DeclStmt) this;
            declStmt.execute(variableMap, funcDefMap, returnStatus);

        } else if (this instanceof AssignStmt) {
            AssignStmt assignStmt = (AssignStmt) this;
            assignStmt.executeAssignmentStatement(variableMap, funcDefMap, returnStatus);

        } else if (this instanceof IfStmt) {
            IfStmt ifStmt = (IfStmt) this;
            result = ifStmt.executeIfStatement(variableMap, funcDefMap, returnStatus);

        } else if (this instanceof IfElseStmt) {
            IfElseStmt ifElseStmt = (IfElseStmt) this;
            result = ifElseStmt.executeIfElseStatement(variableMap, funcDefMap, returnStatus);

        } else if (this instanceof WhileStmt) {
            WhileStmt whileStmt = (WhileStmt) this;
            result = whileStmt.executeWhileStmt(variableMap, funcDefMap, returnStatus);

        } else if (this instanceof PrintStmt) {
            PrintStmt printStmt = (PrintStmt) this;
            printStmt.executePrintStatement(variableMap, funcDefMap, returnStatus);

        } else if (this instanceof BlockStmt) {
            BlockStmt blockStmt = (BlockStmt) this;
            result = blockStmt.executeBlockStatement(variableMap, funcDefMap, returnStatus);

        } else if (this instanceof FunctionCallStmt) {
            FunctionCallStmt callStmt = (FunctionCallStmt) this;
            result = callStmt.executeFucCallStmt(variableMap, funcDefMap, returnStatus);
            
        } else if (this instanceof ReturnStmt) {
            ReturnStmt returnStmt = (ReturnStmt) this;
            result = returnStmt.executeReturnStatement(variableMap, funcDefMap, returnStatus);

        }
        return result;
    }
}
