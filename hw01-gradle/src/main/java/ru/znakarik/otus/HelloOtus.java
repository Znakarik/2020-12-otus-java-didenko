package ru.znakarik.otus;

import com.google.common.collect.Lists;

import java.util.Arrays;

public class HelloOtus {
    public static void main(String[] args) {
        Lists.reverse(Arrays.asList("Otus", "Hello"))
                .forEach(System.out::println);
    }
}
