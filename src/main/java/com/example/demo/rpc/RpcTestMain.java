package com.example.demo.rpc;

public class RpcTestMain {

    public static void main(String[] args) {
        RemoteService service = RpcClient.getProxy(RemoteService.class, "127.0.0.1", 8888);
        System.out.println(service.sayHello());
    }


}
