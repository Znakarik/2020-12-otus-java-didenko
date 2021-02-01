package ru.znakarik.otus.starter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private List<Method> before;
    private List<Method> after;
    private Method test;
    private List<Exception> exceptions;
    private Object testInstance;
    private boolean isPassed;

    public Test(List<Method> before, List<Method> after, Method test, Object testInstance) {
        this.before = before;
        this.after = after;
        this.test = test;
        this.testInstance = testInstance;
        this.exceptions = new ArrayList<>();
        this.isPassed = true;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public Method getTest() {
        return test;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void invokeBefore() {
        this.before.forEach(method ->
        {
            try {
                method.invoke(this.testInstance);
            } catch (Exception e) {
                this.exceptions.add(e);
                this.isPassed = false;
            }
        });
    }

    public void invokeTest() {
        try {
            test.invoke(this.testInstance);
        } catch (Exception e) {
            this.exceptions.add(e);
            this.isPassed = false;
        }
    }

    public void invokeAfter() {
        this.after.forEach(method ->
        {
            try {
                method.invoke(this.testInstance);
            } catch (Exception e) {
                this.exceptions.add(e);
                this.isPassed = false;
            }
        });
    }
}
