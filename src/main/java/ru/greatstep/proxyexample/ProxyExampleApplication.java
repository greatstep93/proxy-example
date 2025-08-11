package ru.greatstep.proxyexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.greatstep.proxyexample.service.JdkProxyService;
import ru.greatstep.proxyexample.service.CglibService;

@SpringBootApplication
public class ProxyExampleApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ProxyExampleApplication.class, args);

        JdkProxyService jdkProxyService = ctx.getBean(JdkProxyService.class);
        System.out.println("Bean class: " + jdkProxyService.getClass().getName());
        System.out.println("Result: " + jdkProxyService.operation());

        System.out.println();

        CglibService cglibService = ctx.getBean(CglibService.class);
        System.out.println("Bean class: " + cglibService.getClass().getName());
        System.out.println("Result: " + cglibService.process());
    }

}
