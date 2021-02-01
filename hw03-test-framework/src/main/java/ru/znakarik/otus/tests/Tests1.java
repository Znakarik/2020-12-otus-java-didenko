package ru.znakarik.otus.tests;

import ru.znakarik.otus.annotations.After;
import ru.znakarik.otus.annotations.Before;
import ru.znakarik.otus.annotations.Test;

public class Tests1 {
    @Before
    public void before() {
        System.out.println("А я @Before из другого класса");
    }

    @Test
    public void test() {
        System.out.println("А я тест из класса " + this.getClass().getCanonicalName());
    }

    @After
    public void after() {
        System.out.println("Кончаю ребят выше");
    }

}
