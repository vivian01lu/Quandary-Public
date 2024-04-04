package ast;

public class QRef extends QVal{
    public QObj referent;

    public QRef(QObj referent) {
        this.referent = referent;
    }
}
