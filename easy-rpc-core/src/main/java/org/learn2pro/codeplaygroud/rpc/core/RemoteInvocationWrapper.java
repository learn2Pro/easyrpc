package org.learn2pro.codeplaygroud.rpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @PACKAGE: org.learn2pro.codeplaygroud.rpc.core
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class RemoteInvocationWrapper implements InvocationHandler {

    /**
     * invoke the rpc method from [remote]
     * @param proxy the proxy rpc provider
     * @param method the proxy rpc method
     * @param args the input arguments
     * @return return the [remote] invocation result
     * @throws Throwable io or inner excetion
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
