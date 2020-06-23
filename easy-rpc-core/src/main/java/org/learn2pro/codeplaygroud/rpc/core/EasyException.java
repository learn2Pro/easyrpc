package org.learn2pro.codeplaygroud.rpc.core;

/**
 * the base easy rpc exception
 */
public class EasyException extends RuntimeException {

    public EasyException(String msg, Throwable parent) {
        super(msg, parent);
    }
}
