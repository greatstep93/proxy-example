package ru.greatstep.proxyexample.service;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.lang.reflect.Proxy;

@Configuration // Класс содержит конфигурацию бинов Spring
public class LoggingProxy {

    @Bean // Создает бин, управляемый Spring-контейнером
    @Primary // Помечает этот бин как основной при внедрении интерфейса Service
    public JdkProxyService serviceProxy(JdkProxyServiceImpl jdkProxyServiceImpl) {
        // Создание JDK динамического прокси для интерфейса Service
        return (JdkProxyService) Proxy.newProxyInstance(
                JdkProxyService.class.getClassLoader(), // 1. Загрузчик классов интерфейса
                new Class[]{JdkProxyService.class}, // 2. Список реализуемых интерфейсов (только Service)

                // 3. Обработчик вызовов (InvocationHandler) с лямбдой
                (proxy, method, args) -> {
                    // Действие ДО вызова целевого метода
                    System.out.println("Before method: " + method.getName());

                    // Вызов реального метода на целевом объекте
                    Object result = method.invoke(jdkProxyServiceImpl, args);

                    // Действие ПОСЛЕ вызова целевого метода
                    System.out.println("After method: " + method.getName());

                    return result; // Возврат результата реального метода
                }
        );
    }

    @Bean
    @Primary
    public CglibService cglibProxy(CglibService target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibService.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            // Действие ДО вызова целевого метода
            System.out.println("Before method: " + method.getName());
            // Вызов реального метода на целевом объекте
            Object result = proxy.invoke(target, args);

            // Действие ПОСЛЕ вызова целевого метода
            System.out.println("After method: " + method.getName());
            return result;
        });
        return (CglibService) enhancer.create();
    }

    /**
     * DefaultAdvisorAutoProxyCreator - это центральный компонент Spring AOP, который: <br>
     * - Автоматически создает AOP-прокси для бинов <br>
     * - Сканирует контекст на наличие советников (Advisors) <br>
     * - Применяет прокси к бинам, соответствующим pointcut-выражениям <br>
     * proxyTargetClass=true	Принудительное использование CGLIB вместо JDK Proxy <br>
     * Почему static?  <br>
     * - Ранняя инициализация: Создается до других бинов (как BeanPostProcessor) <br>
     *
     * - Обязательное требование: Все BeanPostProcessor должны быть объявлены статическими в @Configuration классах <br>
     *
     * - Порядок обработки: Гарантирует, что прокси создадутся до инжектирования зависимостей <br>
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator proxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true); // Для CGLIB
        return creator;
    }
}
