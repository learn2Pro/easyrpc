package org.learn2pro.codeplaygroud.rpc.core;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LocalInvocationWrapper implements InvocationHandler {
    /**
     * invoke the rpc method from local
     *
     * @param proxy  the proxy rpc provider
     * @param method the proxy rpc method
     * @param args   the input arguments
     * @return return the local invocation result
     * @throws Throwable io or inner exception
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws EasyException {
        try {
            Class<?> klazz = proxy.getClass();
            Object provider = ProviderRepository.getInstance().getByName(StringUtils.uncapitalize(klazz.getSimpleName()));
            return ReflectionUtils.invokeMethod(method, provider, args);
        } catch (Exception e) {
            throw new EasyInvokeException("invoke failed,in class:,method:,args:", e);
        }
    }
}
