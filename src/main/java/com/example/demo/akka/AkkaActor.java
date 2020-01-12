package com.example.demo.akka;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;


import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class AkkaActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        //匹配类型，根据不同的类型做不同的处理
        Receive receive= ReceiveBuilder.create().match(AkkaModel.class,s->{
            //单个
            s.setName(s.getName()+" after receive");
            s.incr();
            System.out.println(s.toString());
            AkkaRespModel respModel=new AkkaRespModel();
            respModel.setName("after receive by akkaActor");
            respModel.incr();
            System.out.println(respModel.toString());
            // 从sender()获取调用方，把结果进行返回
            sender().tell(respModel, ActorRef.noSender());
        }).match(String.class,s->{
            //字符串
            System.out.println(s);
        }).match(List.class,s->{
            //列表
            List<ActorRef> actorRefs=new ArrayList<>();
            try{
                List<Future> futures=new ArrayList<>();
                s.stream().forEach(item->{
                    if(item instanceof AkkaModel){
                        ActorRef sender=getContext().getSystem().actorOf(Props.create(AkkaActor.class));
                        actorRefs.add(sender);
                        Future future= Patterns.ask(sender,item,new Timeout(15, TimeUnit.MILLISECONDS));
                        futures.add(future);
                    }
                });
                AkkaRespModel respModel=new AkkaRespModel();
                for(Future future:futures){
                    Object data = AkkaUtils.getObject(future);
                    if(data instanceof AkkaRespModel){
                        respModel.getDatas().add((AkkaRespModel) data);
                    }
                }
                System.out.println(respModel.toString());
                // 从sender()获取调用方，把结果进行返回
                sender().tell(respModel, ActorRef.noSender());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //集体处决，避免内存泄漏
                if(actorRefs.size()>0){
                    actorRefs.parallelStream().forEach(item->item.tell(PoisonPill.getInstance(),ActorRef.noSender()));
                }
            }
        }).build();
        return receive;
    }


}
