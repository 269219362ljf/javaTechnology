package com.example.demo.akka;

import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class AkkaUtils {

    public static Object getObject(Future future) throws InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        CompletionStage jStage= FutureConverters.toJava(future);
        CompletableFuture completableFuture=jStage.toCompletableFuture();
        return completableFuture.get(15, TimeUnit.MILLISECONDS);
    }


}
