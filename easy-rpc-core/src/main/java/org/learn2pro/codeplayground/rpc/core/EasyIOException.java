package org.learn2pro.codeplayground.rpc.core;

public class EasyIOException extends EasyException {
    /**
     * constructor with io exception
     * @param msg the exception message
     * @param parent the parent exception
     */
    public EasyIOException(String msg, Throwable parent) {
        super(msg, parent);
    }
}
