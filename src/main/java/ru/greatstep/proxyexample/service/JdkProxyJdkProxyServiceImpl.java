package ru.greatstep.proxyexample.service;

import org.springframework.stereotype.Component;

@Component
public class JdkProxyJdkProxyServiceImpl implements JdkProxyService {
    @Override
    public String operation() {
        return internalOperation();
    }

    public String internalOperation() {
        return "JdkProxyJdkProxyServiceImpl work";
    }
}
