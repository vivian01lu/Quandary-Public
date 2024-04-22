package ast;

import java.util.concurrent.atomic.AtomicBoolean;

public class QObj extends QVal{
    public QVal left;
    public QVal right;
    AtomicBoolean lock; // = new AtomicBoolean(false);
    //constructor
    public QObj(QVal left, QVal right) {
        this.left = left;
        this.right = right;
        this.lock = new AtomicBoolean(false);
    }
    public void acq() {
        //System.out.println("acquiring lock " + this.toString());
        while (!this.lock.compareAndSet(false, true)) {
            //System.out.println("re-acquiring lock " + this.toString());
            try {
                //Thread.sleep(3000);
            }
            catch (Exception ex) {

            }
        }
        //System.out.println("locked " + this.toString());
    }

    public void rel() {
        //System.out.println("releasing: " + this.toString());
        while (!this.lock.compareAndSet(true, false)) {
            //System.out.println("releasing lock attempt " + this.toString());
            try {
                //Thread.sleep(3000);
            }
            catch (Exception ex) {

            }
        }
        //System.out.println("released " + this.toString());
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
