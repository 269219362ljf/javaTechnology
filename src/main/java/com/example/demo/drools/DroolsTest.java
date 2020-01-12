package com.example.demo.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args){
        KieServices kieServices=KieServices.Factory.get();
        KieContainer container=kieServices.getKieClasspathContainer();
        KieSession session=container.newKieSession("session");
        int count=session.fireAllRules();
        System.out.println("exe rule count:"+count);
        session.dispose();
    }
}
