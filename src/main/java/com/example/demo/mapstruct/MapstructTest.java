package com.example.demo.mapstruct;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class MapstructTest {

    public static void main(String[] args){
        MapstructMapper mapper=MapstructMapper.MAPPER;
        TestStruct struct=new TestStruct();
        Map map=new HashMap();
        map.put("a","a");
        struct.setTestMap(map);
        struct.setTestString("testStruct");
        struct.setStructValue(1.5579878);
        System.out.println("**********************************");
        System.out.println("init struct:"+ JSON.toJSONString(struct));
        //System.out.println("after struct:"+ JSON.toJSONString(mapper.dataToEntity(struct)));
        TestEntity entity=mapper.dataToEntity(struct);
        System.out.println("dataToEntity entity:"+JSON.toJSONString(entity));
        System.out.println("**********************************");
        entity.getTestMap().put("entityA","entityA");
        struct.getTestMap().put("after struct","after struct");
        System.out.println("after 1 struct:"+ JSON.toJSONString(struct));
        System.out.println("after 1 entity:"+ JSON.toJSONString(entity));
        System.out.println("**********************************");
        TestStruct afterStruct=mapper.entityToData(entity);
        System.out.println("after 2 struct:"+ JSON.toJSONString(afterStruct));
        System.out.println("after 2 entity:"+ JSON.toJSONString(entity));
        System.out.println("**********************************");



    }






}
