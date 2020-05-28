package com.example.demo;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetTest {

    @Test
    public void test(){
        Set<Integer> oldset=new HashSet<>();
        oldset.addAll(Arrays.asList(1,2,3,4,256));
        Set<Integer> newset=new HashSet<>();
        newset.addAll(Arrays.asList(1,2,256,5,6,7));
        Set diff= Sets.difference(oldset,newset);
        diff.stream().forEach(System.out::println);
    }










}
