package ast;

import java.util.HashMap;

public class Cond extends ASTNode{

    public static final int LE = 4;
    public static final int GE = 5;
    public static final int EQ = 6;
    public static final int NE = 7;
    public static final int LT = 8;
    public static final int GT = 9;
    public static final int AND = 10;
    public static final int OR = 11;
    public static final int NOT = 0;

    //every condition has a left expression, a right expression and an operator or a left condition and a right condition and an operator
    final Expr expr1;
    final int operator;
    final Expr expr2;
    final Cond cond1;
    final Cond cond2;
    
    public Cond(Cond cond1, int operator, Cond cond2 ,Location loc) {
        super(loc);
        this.expr1 = null;
        this.operator = operator;
        this.expr2 = null;  
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    public Cond(Cond cond1, int operator,Location loc) {
        super(loc);
        this.expr1 = null;
        this.operator = operator;
        this.expr2 = null;  
        this.cond1 = cond1;
        this.cond2 = null;       
    }
    public Cond( Expr expr1, int operator, Expr expr2, Location loc) {
        super(loc);
        this.expr1 = expr1;
        this.operator = operator;
        this.expr2 = expr2;
        this.cond1 = null;
        this.cond2 = null;
    }
   
    public boolean execute(HashMap<String, QVal> variableMap, HashMap<String, FuncDef> funcDefMap) {
        boolean result = false;
        QInt leftCond;
        QInt rightCond;
        switch (operator) {
            case 4:
                leftCond = (QInt)expr1.execute(variableMap, funcDefMap);
                rightCond = (QInt)expr2.execute(variableMap, funcDefMap);
                result = leftCond.getValue() <= rightCond.getValue();
                break;
            case 5:
                leftCond = (QInt)expr1.execute(variableMap, funcDefMap);
                rightCond = (QInt)expr2.execute(variableMap, funcDefMap);
                result =  leftCond.getValue() >= rightCond.getValue();
                break;
            case 6:
                leftCond = (QInt)expr1.execute(variableMap, funcDefMap);
                rightCond = (QInt)expr2.execute(variableMap, funcDefMap);
                result = leftCond.getValue().equals(rightCond.getValue());
                break;
            case 7:
                leftCond = (QInt)expr1.execute(variableMap, funcDefMap);
                rightCond = (QInt)expr2.execute(variableMap, funcDefMap);
                result = !leftCond.getValue().equals(rightCond.getValue());
              
                break;
            case 8:
                leftCond = (QInt)expr1.execute(variableMap, funcDefMap);
                rightCond = (QInt)expr2.execute(variableMap, funcDefMap);
                result = leftCond.getValue() < rightCond.getValue();
                break;
            case 9:
                leftCond = (QInt)expr1.execute(variableMap, funcDefMap);
                rightCond = (QInt)expr2.execute(variableMap, funcDefMap);
                result = leftCond.getValue() > rightCond.getValue();
                break;
            case 10:
                result = cond1.execute(variableMap, funcDefMap) && cond2.execute(variableMap, funcDefMap);
                break;
            case 11:
                result = cond1.execute(variableMap, funcDefMap) || cond2.execute(variableMap, funcDefMap);
                break;
            case 0:
                result = !cond1.execute(variableMap, funcDefMap);
                break;
            default:
                break;
        }
        return result;
    }
}