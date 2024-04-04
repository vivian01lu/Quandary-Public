package ast;

import java.util.HashMap;
import java.util.List;


public class FuncDef extends ASTNode {

    private final VarDecl varDecl;
    private final List<VarDecl> params;
    private final StmtList stmtList;
    final HashMap<String, Boolean> mutMap = new HashMap<>();
  
   
    private final HashMap<String, Type.TYPE> typeMap = new HashMap<>();

    public FuncDef(VarDecl varDecl, List<VarDecl> params, List<Stmt> stmtList, Location loc) {
        super(loc);
        this.varDecl = varDecl;
        this.params = params;
        this.stmtList = new StmtList(stmtList, loc);
    }

    public HashMap<String, Type.TYPE> getType() {
        return typeMap;
    }
    //this map is to help to check if the variable is mutable or not
    public HashMap<String, Boolean> mutMap() {
        return mutMap;
    }

    public VarDecl getVarDecl() {
        return varDecl;
    }
    //this is for getting the parameters of the function,it is a replace for FormalDecrlist
    public List<VarDecl> getParams() {
        return params;
    }
    

    public StmtList getStmtList() {
        return stmtList;
    }

    public Location getLoc() {
        return loc;
    }
    @Override
    public String toString() {
        return "function " + varDecl.getType() + " " + varDecl.getIdent() +
                "(" + params + ") " + stmtList;
    }


    public QVal execute(HashMap<String, FuncDef> funcDefMap, List<QVal> arList) {
        HashMap<String,QVal> variableMap = new HashMap<>();
        ReturnStatus returnStatus = new ReturnStatus();
        returnStatus.setStatus(false);

        //initialize the parameters
        for (int i = 0; i < params.size(); i++) {
            
            VarDecl varDecl = params.get(i);
            if (varDecl.IsMut()){
                //save the mutability of variables in the mutMap
                this.mutMap().put(varDecl.getIdent(), true);
            } else{
                this.mutMap().put(varDecl.getIdent(), false);
            }
            //save the parameters in the variableMap and typeMap
            variableMap.put(varDecl.getIdent(), arList.get(i));
            typeMap.put(varDecl.getIdent(), params.get(i).getType());
        }
        //execute the function      
        return stmtList.execute(variableMap, funcDefMap, returnStatus);
    }
}
