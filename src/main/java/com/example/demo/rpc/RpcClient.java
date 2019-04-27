package com.example.demo.rpc;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class RpcClient {

    private Socket sock;

    public RpcClient() {
    }

    public static <T> T getProxy(Class<T> clazz, String ip, int port) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz}, new RpcInvocationHandler(ip, port));
    }

    public Object invoke(RpcRequest request, String Ip, int port) throws IOException {
        sock = new Socket(Ip, port);
        InputStream in = sock.getInputStream();
        OutputStream out = sock.getOutputStream();
        ObjectOutputStream objOut;
        ObjectInputStream objIn;
        RpcResponse response = null;
        try {
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(request);
            objOut.flush();
            objIn = new ObjectInputStream(in);
            Object res = objIn.readObject();
            if (res instanceof RpcResponse) {
                response = (RpcResponse) res;
            } else {
                throw new RuntimeException("返回對象不正确!!!");
            }
        } catch (Exception e) {
            System.out.println("error:   " + e.getMessage());
        } finally {
            out.close();
            in.close();
            sock.close();
            return response.getResult();
        }
    }

    private static class RpcInvocationHandler implements InvocationHandler {

        private RpcClient rpcClient;
        private int port;
        private String ip;

        public RpcInvocationHandler(String ip, int port) {
            this.rpcClient = new RpcClient();
            this.port = port;
            this.ip = ip;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest request = new RpcRequest();
            request.setClassName(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setParamTypes(method.getParameterTypes());
            request.setParams(args);
            return rpcClient.invoke(request, ip, port);
        }
    }


}
