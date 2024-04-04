package ast;

public class VarDecl {
    //these are for the VarDecl node
    private final boolean isMut;
    private final Type.TYPE type;
    private final String ident;
    private final Location loc;

    public VarDecl(boolean isMut, Type.TYPE type, String ident, Location loc) {
        this.isMut = isMut;
        this.type = type;
        this.ident = ident;
        this.loc = loc;
    }

    public boolean IsMut() {
        return isMut;
    }

    public Type.TYPE getType() {
        return type;
    }

    public String getIdent() {
        return ident;
    }
}
