package com.example.demo.akka;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class AkkaRespModel {
    private String name;
    private int count=0;
    private List<AkkaRespModel> datas=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AkkaRespModel> getDatas() {
        return datas;
    }

    public void setDatas(List<AkkaRespModel> datas) {
        this.datas = datas;
    }

    public void incr(){
        this.count++;
    }

    @Override
    public String toString() {
        return "AkkaRespModel{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", datas=" + datas +
                '}';
    }
}
