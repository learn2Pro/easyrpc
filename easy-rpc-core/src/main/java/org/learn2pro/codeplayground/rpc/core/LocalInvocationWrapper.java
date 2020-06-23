package org.learn2pro.codeplayground.rpc.core;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class LocalInvocationWrapper implements InvocationHandler {

    /**
     * the target bean name
     */
    private String targetName;

    public LocalInvocationWrapper(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

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
            Object provider = ProviderRepository.getInstance().getByName(targetName);
            return ReflectionUtils.invokeMethod(method, provider, args);
        } catch (Exception e) {
            throw new EasyInvokeException("invoke failed,in class:" + proxy.getClass().getSimpleName() + ",method:" + method.getName() + ",args:" + Arrays.toString(args), e);
        }
    }


}
