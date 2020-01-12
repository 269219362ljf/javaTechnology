package com.example.demo.complie;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class ComplieTestMain {


    //源代码中，package必须在动态加载前已经存在
    private String code="package com.example.demo.complie.test; import java.io.FilterOutputStream;  public class GenrateCodeClass{" +
            "public void showData(){System.out.println(\"a\");}" +
            "}";


    public void main() throws Exception{
        // 真正执行编译过程的编译器
        JavaCompiler compiler= ToolProvider.getSystemJavaCompiler();
        //StandardJavaFileManager 对象主要负责编译文件对象的创建，编译的参数等等
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        //继承ForwardingJavaFileManager
        MemoryJavaFileManager manager=new MemoryJavaFileManager(stdManager);
        // 诊断信息收集器
        DiagnosticCollector diagnosticCollector = new DiagnosticCollector();
        // 构建动态java文件，simpleName必须为类名，contents为源代码
        JavaFileObject javaFileObject = new StringInputBuffer("GenrateCodeClass", code);
        // 构建编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnosticCollector, null, null, Arrays.asList(javaFileObject));
        // 编译任务执行
        boolean result=task.call();
        //如果编译不通过
        if (!result) {
            // 收集编译结果
            List list = diagnosticCollector.getDiagnostics();
            StringBuilder errBuilder = new StringBuilder();
            for (Object object : list) {
                Diagnostic d = (Diagnostic) object;
                errBuilder.append(d.getMessage(Locale.ENGLISH)).append("\n");
            }

            throw new Exception(errBuilder.toString());
        }
        //获得编译后类名和字节码
        Map<String, byte[]> map=manager.getClassBytes();
        //加载完成的类
        Class cls=null;
        for(String key:map.keySet()){
            //类加载
            EngineClassLoader classLoader=new EngineClassLoader(key,map.get(key));
            try {
                cls=classLoader.loadClass(key);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(cls.getName());
        //由于这个类加载器和主线程的加载器不是同一个,不能通过Class.forName来获取
        Object instance=cls.getDeclaredConstructor().newInstance();
        Method method = cls.getDeclaredMethod("showData");
        method.invoke(instance);
        //实例化后，可通过class和实例进行spring注册

    }

    class StringInputBuffer extends SimpleJavaFileObject {
        // 存放java源码内容
        private String contents;

        public StringInputBuffer(String simpleName, String contents) throws URISyntaxException {
            super(new URI(simpleName + ".java"), Kind.SOURCE);
            this.contents = contents;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return contents;
        }

    }

    //ForwardingJavaFileManager实现从内存中直接加载Java源文件
    class MemoryJavaFileManager extends ForwardingJavaFileManager {

        private Map<String, byte[]> classBytes;

        public MemoryJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
            classBytes = new HashMap<String, byte[]>();
        }

        public Map<String, byte[]> getClassBytes() {
            return classBytes;
        }

        @Override
        public void close() throws IOException {
            classBytes.clear();
        }




        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {

            if (kind == JavaFileObject.Kind.CLASS) {
                try {
                    return new ClassOutputBuffer(className);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                return super.getJavaFileForOutput(location, className, kind, sibling);
            } else {
                return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        }

        private class ClassOutputBuffer extends SimpleJavaFileObject {
            private String name;

            ClassOutputBuffer(String name) throws URISyntaxException {
                super(new URI(name + ".java"), Kind.CLASS);
                this.name = name;
            }

            @Override
            public OutputStream openOutputStream() {
                return new FilterOutputStream(new ByteArrayOutputStream()) {
                    @Override
                    public void close() throws IOException {
                        out.close();
                        ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                        classBytes.put(name, bos.toByteArray());
                    }
                };
            }
        }
    }

    class EngineClassLoader extends ClassLoader {

        private String className;
        private byte[] classBytes;

        public EngineClassLoader(String className, byte[] classBytes) {
            this.className = className;
            this.classBytes = classBytes;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if(name.equals(className)){
                return defineClass(name, classBytes, 0, classBytes.length);
            }
            else{
                return super.findClass(name);
            }

        }
        //用于获取类名
        private String getSimpleClassName(String name) {

            if (name.lastIndexOf(".") >= 0) {
                return name.substring(name.lastIndexOf(".") + 1);
            }

            return name;
        }


    }






    public static void main(String[] args){
        ComplieTestMain complieTestMain=new ComplieTestMain();
        try {
            complieTestMain.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
