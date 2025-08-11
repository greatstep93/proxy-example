package ru.greatstep.proxyexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CglibService {

    @Autowired
    private CglibService cglibService;

    public String process() {
        return cglibService.internalProcess();
    }

    public String internalProcess() {
        return "CglibService work";
    }
}
