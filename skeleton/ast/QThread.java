
package ast;
import java.util.HashMap;


public class QThread extends Thread {
    private final Expr e;
    private QVal result;
    private HashMap<String, QVal> variableMap;
    private HashMap<String, FuncDef> funcDefMap;
    //constructor for QThread
    public QThread(Expr expr, HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        super();
        this.e = expr;
        this.variableMap = variableMap;
        this.funcDefMap = funcDefMap;
    }
    //get the result of the thread
    public QVal getResult() {
        return result;
    }

    @Override
    public void run() {     
        result = e.execute(variableMap, funcDefMap);
    }
}