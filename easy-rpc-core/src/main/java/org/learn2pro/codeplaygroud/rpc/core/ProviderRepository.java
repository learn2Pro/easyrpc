package org.learn2pro.codeplaygroud.rpc.core;

import com.google.common.base.Preconditions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * the provider repo in [remote and local]
 *
 * @PACKAGE: org.learn2pro.codeplaygroud.rpc.core
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class ProviderRepository implements Repository, ApplicationContextAware {
    /**
     * the app context in spring
     */
    private ApplicationContext app;
    private static final ProviderRepository INSTANCE = new ProviderRepository();

    public static ProviderRepository getInstance() {
        return INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getByName(String name) throws Exception {
        Preconditions.checkArgument(app != null, "app context is empty,pls check!");
        return (T) app.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        app = applicationContext;
    }
}
