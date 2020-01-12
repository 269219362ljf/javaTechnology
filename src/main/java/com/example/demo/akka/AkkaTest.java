package com.example.demo.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AkkaTest {

    private static final Timeout timeout=new Timeout(500, TimeUnit.MILLISECONDS);

    public static void main(String[] args) throws Exception{
        //创建akka管理器
        ActorSystem actorSystem=ActorSystem.create();
        //通过使用akka的props构建一个actor，可以增加命名，但不能重复相同命名，返回一个actor的指向引用
        ActorRef sender=actorSystem.actorOf(Props.create(AkkaActor.class));
        //构建发送的消息
        AkkaModel msg=new AkkaModel();
        msg.setName("i am msg");
        msg.setValue(0);
        System.out.println(msg.toString());
        sender.tell(msg,sender);
        Object data=AkkaUtils.getObject(Patterns.ask(sender,msg,timeout));
        System.out.println("end point 1:"+((AkkaRespModel)data).toString());
        AkkaModel msg1=new AkkaModel();
        msg1.setName("i am msg1");
        msg1.setValue(1);
        data=AkkaUtils.getObject(Patterns.ask(sender, Arrays.asList(msg,msg1),timeout));
        System.out.println("end point 2:"+((AkkaRespModel)data).toString());





    }


}
