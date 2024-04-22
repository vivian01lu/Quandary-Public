package ast;

import javax.print.DocFlavor.INPUT_STREAM;

public class QInt extends QVal{
    public Long value;
    //constructor
    public QInt(Long value) {
        this.value = value;
    }
    public QInt(int value) {
        this.value = (long)value;
    }

    //get the long value:
    public Long getValue() {
        return this.value;
    }
    //toString()
    public String toString() {
        return value.toString();
    }
}
