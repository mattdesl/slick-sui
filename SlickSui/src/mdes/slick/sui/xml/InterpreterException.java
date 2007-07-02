/*
 * InterpreterException.java
 *
 * Created on June 24, 2007, 7:58 PM
 */

package mdes.slick.sui.xml;

/**
 *
 * @author davedes
 */
public class InterpreterException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>InterpreterException</code> without detail message.
     */
    public InterpreterException() {
    }
    
    /**
     * Constructs an instance of <code>InterpreterException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InterpreterException(String msg) {
        super(msg);
    }
    
    public InterpreterException(Throwable t) {
        super(t);
    }
}
