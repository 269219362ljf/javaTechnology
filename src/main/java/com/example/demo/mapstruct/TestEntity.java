package com.example.demo.mapstruct;

import java.util.Map;

public class TestEntity {

    private String testString;

    private Map testMap;

    private Integer entityValue;

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public Map getTestMap() {
        return testMap;
    }

    public void setTestMap(Map testMap) {
        this.testMap = testMap;
    }

    public Integer getEntityValue() {
        return entityValue;
    }

    public void setEntityValue(Integer entityValue) {
        this.entityValue = entityValue;
    }
}
