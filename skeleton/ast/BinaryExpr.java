package ast;

import java.util.HashMap;
import java.util.Map;

public class BinaryExpr extends Expr {

    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int MULTIPLY = 3; // new constant for the multiplication operator

    public static final int DOT = 4;




    final Expr expr1;
    final int operator;
    final Expr expr2;

    public BinaryExpr(Expr expr1, int operator, Expr expr2, Location loc) {
        super(loc);
        this.expr1 = expr1;
        this.operator = operator;
        this.expr2 = expr2;
    }
    

    public Expr getLeftExpr() {
        return expr1;
    }

    public int getOperator() {
        return operator;
    }
    
    public Expr getRightExpr() {
        return expr2;
    }

    @Override
    public String toString() {
        String s = null;
        switch (operator) {
            case PLUS:  s = "+"; break;
            case MINUS: s = "-"; break;
            case MULTIPLY: s = "*"; break;
            case DOT: s = "."; break;
        }
        return "(" + expr1 + " " + s + " " + expr2 + ")";
    }

    public QVal execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        QVal result = null;
       
        switch (operator) {
            case PLUS:
                QInt left1 = (QInt)expr1.execute(variableMap, funcDefMap);
                QInt right1 = (QInt)expr2.execute(variableMap, funcDefMap);
                result = new QInt(left1.getValue() + right1.getValue());
                break;
            case MINUS:
                QInt left2 = (QInt)expr1.execute(variableMap, funcDefMap);
                QInt right2 = (QInt)expr2.execute(variableMap, funcDefMap);
                result = new QInt(left2.getValue() - right2.getValue());
                break;
            case MULTIPLY:
                QInt left3 = (QInt)expr1.execute(variableMap, funcDefMap);
                QInt right3 = (QInt)expr2.execute(variableMap, funcDefMap);
                result = new QInt(left3.getValue() * right3.getValue());
                break;
            case DOT:
               //using QRef()
               QVal left = expr1.execute(variableMap, funcDefMap);
               QVal right = expr2.execute(variableMap, funcDefMap);
               return new QRef(new QObj(left, right));

        }
        return result;
    }
}
