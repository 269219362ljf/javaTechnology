package com.example.demo.rpc;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RpcService implements Runnable {

    private Socket sockClient;
    private Map<String, Class> serviceRegistry = new HashMap<String, Class>();
    private RpcResponse response = new RpcResponse();

    public RpcService(Socket sock) {
        super();
        this.sockClient = sock;
    }

    public void run() {
        try {
            OutputStream out = sockClient.getOutputStream();
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream in = sockClient.getInputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            // 2. 获取请求数据，强转参数类型
            Object param = objIn.readObject();
            RpcRequest request = null;
            response.setResult("test rpc");
//            if (!(param instanceof RpcRequest)) {
//                response.setResult("参数错误");
//                objOut.writeObject(response);
//                objOut.flush();
//                return;
//            } else {
//                request = (RpcRequest) param;
//            }
//            // 3. 查找并执行服务方法
//            System.out.println("要執行的类型为：" + request.getClassName());
//            Class<?> service = serviceRegistry.get(request.getClassName());
//            if (service != null) {
//                try {
//                    Method method = service.getMethod(request.getMethodName(), request.getParamTypes());
//                    Object result = method.invoke(service.newInstance(), request.getParams());
//                    // 4. 得到结果并返回
//                    response.setResult(result);
//                } catch (Exception e) {
//                    response.setError(e);
//                }
//            }
            objOut.writeObject(JSON.toJSONString(response));
            objOut.flush();
            out.close();
            in.close();
            sockClient.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerService(Class iface, Class Imp) {
        this.serviceRegistry.put(iface.getName(), Imp);
    }


//    private Socket client;
//    private Map<String, Object> services;
//
//    /**
//     * @param client   客户端
//     * @param services 所有服务
//     */
//    public RpcService(Socket client, Map<String, Object> services) {
//        super();
//        this.client = client;
//        this.services = services;
//    }
//
//
//    public void run() {
//        InputStream in = null;
//        ObjectInputStream oin = null;
//        OutputStream out = null;
//        ObjectOutputStream oout = null;
//        RpcResponse response = new RpcResponse();
//        try {
//            // 1. 获取流
//            in = client.getInputStream();
//            oin = new ObjectInputStream(in);
//            out = client.getOutputStream();
//            oout = new ObjectOutputStream(out);
//
//            // 2. 获取请求数据，强转参数类型
//            Object param = oin.readObject();
//            RpcRequest request = null;
//            if (!(param instanceof RpcRequest)) {
//                response.setError(new Exception("参数错误"));
//                oout.writeObject(response);
//                oout.flush();
//                return;
//            } else {
//                request = (RpcRequest) param;
//            }
//
//            // 3. 查找并执行服务方法
//            Object service = services.get(request.getClassName());
//            Class<?> clazz = service.getClass();
//            Method method = clazz.getMethod(request.getMethodName(), request.getParamTypes());
//            Object result = method.invoke(service, request.getParams());
//            // 4. 得到结果并返回
//            response.setResult(result);
//            oout.writeObject(response);
//            oout.flush();
//            return;
//        } catch (Exception e) {
//            try {    //异常处理
//                if (oout != null) {
//                    response.setError(e);
//                    oout.writeObject(response);
//                    oout.flush();
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//            return;
//        } finally {
//            try {    // 关闭流
//                if (in != null) in.close();
//                if (oin != null) oin.close();
//                if (out != null) out.close();
//                if (oout != null) oout.close();
//                if (client != null) client.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
