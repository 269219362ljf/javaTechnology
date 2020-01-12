package com.example.demo.akka;

import lombok.Data;


public class AkkaModel {

    private String name;
    private int value=0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void incr(){
        this.value++;
    }

    @Override
    public String toString() {
        return "AkkaModel{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
