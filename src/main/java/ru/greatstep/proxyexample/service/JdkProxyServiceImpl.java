package ru.greatstep.proxyexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdkProxyServiceImpl implements JdkProxyService {

    private JdkProxyService jdkProxyServiceImpl;

    @Autowired
    private void setJdkProxyServiceImpl(JdkProxyService jdkProxyServiceImpl) {
        this.jdkProxyServiceImpl = jdkProxyServiceImpl;
    }

    @Override
    public String operation() {
        return jdkProxyServiceImpl.internalOperation();
    }

    @Override
    public String internalOperation() {
        return "JdkProxyJdkProxyServiceImpl work";
    }
}
