package ru.znakarik.otus.starter;

import ru.znakarik.otus.annotations.After;
import ru.znakarik.otus.annotations.Before;
import ru.znakarik.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {
    private Class<?> testClass;
    private List<ru.znakarik.otus.starter.Test> testList;
    private Reporter reporter;

    public void setTestClass(Class<?> testClass) {
        this.testClass = testClass;
    }

    public void createReporter() {
        reporter = new Reporter(testList);
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void createTestsInClass() {
        List<Method> before = getMethodsByAnnotation(Before.class);
        List<Method> after = getMethodsByAnnotation(After.class);
        List<Method> tests = getMethodsByAnnotation(Test.class);
        List<ru.znakarik.otus.starter.Test> testList = new ArrayList<>();
        tests.forEach(test -> {
            try {
                testList.add(new TestImpl(before, after, test, this.testClass.getConstructor().newInstance()));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        this.testList = testList;
    }

    private List<Method> getMethodsByAnnotation(Class<? extends Annotation> annotation) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    public void startTests() {
        this.testList.forEach(test -> {
            test.invokeBefore();
            test.invokeTest();
            test.invokeAfter();
        });
    }
}
