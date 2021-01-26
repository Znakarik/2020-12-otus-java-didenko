package ru.znakarik.otus.starter;

import java.lang.reflect.Method;
import java.util.List;

public interface Test {
    void invokeBefore();

    void invokeTest();

    void invokeAfter();

    boolean isPassed();

    Method getTest();

    List<Exception> getExceptions();
}
