package com.example.demo;


import com.example.demo.service.async.AsyncTaskService;
import com.example.demo.service.event.DemoPublisher;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoServiceTest extends DemoApplicationTests {

    Logger logger = LoggerFactory.getLogger(DemoServiceTest.class);

    @Autowired
    DemoPublisher publisher;

    @Autowired
    AsyncTaskService asyncTaskService;

    @Test
    public void testPublish(){
        publisher.publish("测试1");
        publisher.anotherPublish("测试2");
    }

    @Test
    public void testAsync(){
        for(int i=0;i<25;i++){
            asyncTaskService.executeAsyncTask(i);
        }
    }


}
