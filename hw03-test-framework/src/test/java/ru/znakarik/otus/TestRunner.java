package ru.znakarik.otus;

import ru.znakarik.otus.starter.Runner;
import ru.znakarik.otus.tests.Tests2;
import ru.znakarik.otus.tests.Tests1;
import ru.znakarik.otus.tests.Tests3;

import java.util.Arrays;

public class TestRunner {
    public static void main(String[] args) {
        Runner runner = new Runner();
        Arrays.asList(Tests2.class, Tests3.class, Tests1.class).forEach(
                testClass -> {
                    runner.setTestClass(testClass);
                    runner.createTestsInClass();
                    runner.startTests();
                    runner.createReporter();
                    runner.getReporter().printAllExceptions();
                }
        );
    }
}
