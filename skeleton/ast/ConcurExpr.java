package ast;
import java.util.HashMap;
public class ConcurExpr extends Expr{
    final BinaryExpr e;

    public ConcurExpr(BinaryExpr e, Location loc) {
        super(loc);
        this.e = e;
    }

    public BinaryExpr getExpr() {
        return e;
    }

    //execute the concurrent expression
    public QVal executeConcurrency(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
      //get the binary expression from the concurrent expression
        QThread t1 = new QThread(e, variableMap, funcDefMap,1);
        QThread t2 = new QThread(e, variableMap, funcDefMap,2);
         //start the threads
         System.out.println("Starting threads 1");
         t1.start();
         System.out.println("Starting threads 2");
         t2.start();
        //join the threads
        try {
            System.out.println("joining threads 1");
            t1.join();
            System.out.println("joining threads 2");
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("threads completes");
        QVal leftVal = t1.getFirstQVal();
        QVal rightVal = t2.getSecondQVal();
      
        //return the result of the concurrent expression
        switch (e.getOperator()) {
            case 1:
                //plus operator   
                QInt left = (QInt)leftVal;
                QInt right = (QInt)rightVal;
                Long leftLong = left.getValue();
                Long rightLong = right.getValue();      
                return new QInt(leftLong + rightLong);
              
            case 2:
                //minus operator
                QInt left2 = (QInt)leftVal;
                QInt right2 = (QInt)rightVal;
                Long leftLong2 = left2.getValue();
                Long rightLong2 = right2.getValue();

                return new QInt(leftLong2 - rightLong2);
               
            case 3:
                //multiply operator
                QInt left3 = (QInt)leftVal;
                QInt right3 = (QInt)rightVal;
                Long leftLong3 = left3.getValue();
                Long rightLong3 = right3.getValue();
                return new QInt(leftLong3 * rightLong3);
                
            case 4:
                //dot operator
               
                QObj obj = new QObj(leftVal, rightVal);
                QRef ref = new QRef(obj);
                return ref;
                
            default:
                break;
        }
        return null;
    }
    
}
