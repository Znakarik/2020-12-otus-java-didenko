package ru.znakarik.otus.tests;

import ru.znakarik.otus.annotations.Before;
import ru.znakarik.otus.annotations.Test;

public class Tests3 {
    @Before
    public void before() {
        throw new IllegalArgumentException();
    }

    @Test
    public void test() throws Exception {
        throw new Exception();
    }
}
