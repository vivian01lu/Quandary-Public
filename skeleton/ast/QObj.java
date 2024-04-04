package ast;

public class QObj{
    QVal left;
    QVal right;
    //constructor
    public QObj(QVal left, QVal right) {
        this.left = left;
        this.right = right;
    }
    //get left and right
    public QVal getLeft() {
        return left;
    }

    public QVal getRight() {
        return right;
    }

}
