
package ast;
import java.util.HashMap;


public class QThread extends Thread {
    private final BinaryExpr e;
    private QVal firstQVal;
    private QVal secondQVal;
    private HashMap<String, QVal> variableMap;
    private HashMap<String, FuncDef> funcDefMap;
    private int threadID;
    //constructor for QThread
    public QThread(BinaryExpr expr, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap,int threadID) {
        this.e = expr;
        this.variableMap = variableMap;
        this.funcDefMap = funcDefMap;
        this.threadID = threadID;
    }
    //get the first value
    public QVal getFirstQVal() {
        return this.firstQVal;
    }
    //get the second value
    public QVal getSecondQVal() {
        return this.secondQVal;
    }
    
    public void run() {   
        System.out.println("Thread ID: " + threadID); 
        if(threadID == 1){
            firstQVal = this.e.getLeftExpr().execute(variableMap,funcDefMap);
            System.out.println("First Value: " + firstQVal.toString());


        }else if(threadID == 2){
            secondQVal  =this.e.getRightExpr().execute(variableMap,funcDefMap);
            System.out.println("Second Value: " + secondQVal.toString());
        }
    }
}