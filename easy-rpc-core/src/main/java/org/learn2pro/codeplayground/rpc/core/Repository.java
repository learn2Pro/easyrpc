package org.learn2pro.codeplayground.rpc.core;

import java.lang.reflect.Proxy;

public interface Repository {
    /**
     * get the instance in repository
     *
     * @param name the instance unique name
     * @param <T>  the generic type of instance
     * @return the singleton instance in jvm
     */
    <T> T getByName(String name) throws Exception;

}
