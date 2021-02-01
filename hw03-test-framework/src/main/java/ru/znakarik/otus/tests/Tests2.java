package ru.znakarik.otus.tests;

import ru.znakarik.otus.annotations.Before;
import ru.znakarik.otus.annotations.Test;

import java.io.FileNotFoundException;

public class Tests2 {
    @Before
    public void invokeBeforeBefore() throws FileNotFoundException {
        System.out.println("А я вот самый первый");
        throw new FileNotFoundException();
    }

    @Before
    public void invokeBefore() {
        System.out.println("А я вот выполняюсь перед всеми");
    }

    @Test
    public void toInvoke() {
        System.out.println("Hashcode 1 теста:" + this.hashCode());
        Object o = null;
        o.toString();
        System.out.println("Меня вызывали?");
    }

    @Test
    public void toInvokeAgain() {
        System.out.println("Hashcode 2 теста:" + this.hashCode());
        System.out.println("Опять блин?");
    }
}
