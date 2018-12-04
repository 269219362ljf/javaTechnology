package com.example.demo;

import com.example.demo.neo4j.UserNode;
import com.example.demo.neo4j.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryTest extends DemoApplicationTests{

    Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUserNode(){
        System.out.println(userRepository);
        UserNode userNode = new UserNode();
        userNode.setName("老鼠");
        userNode.setUserId("123");
        UserNode save = userRepository.save(userNode);
        logger.info(save.toString());
        Assert.assertTrue(save!=null);
    }

    @Test
    public void delAll(){
        userRepository.deleteAll();
    }


}
