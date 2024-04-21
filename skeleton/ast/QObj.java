package ast;

import java.util.concurrent.atomic.AtomicBoolean;

public class QObj extends QVal{
    public QVal left;
    public QVal right;
    AtomicBoolean lock = new AtomicBoolean(false);
    //constructor
    public QObj(QVal left, QVal right) {
        this.left = left;
        this.right = right;
    }
    public void acq() {
        while (!this.lock.compareAndSet(false, true)) {}
    }

    public void rel() {
        this.lock.compareAndSet(true, false);
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
