package ast;

public class Type {
    public static enum TYPE {
        INT,
        REF,
        Q
    }

    private final TYPE type;

    public Type(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }
}