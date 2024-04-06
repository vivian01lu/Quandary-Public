package ast;

public class QObj extends QVal{
    public QVal left;
    public QVal right;
    //constructor
    public QObj(QVal left, QVal right) {
        this.left = left;
        this.right = right;
    }
    //get left and right
    public QVal getLeft() {
        return this.left;
    }

    public QVal getRight() {
        return this.right;
    }


    public String toString() {
        String leftStr = "";
        if (left == null){
            leftStr = "nil";
        }else {
            leftStr = left.toString();
        }
        String rightStr = "";
        if (right == null){
            rightStr = "nil";
        }else {
            rightStr = right.toString();
        }
        return "("+ leftStr + " . " +  rightStr +")";
    }
}
