package ru.greatstep.proxyexample.service;

import org.springframework.stereotype.Component;

@Component
public class CglibService {

    public String process() {
        return "CglibService work";
    }

}
