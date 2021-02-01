package ru.znakarik.otus;

import ru.znakarik.otus.starter.Runner;
import ru.znakarik.otus.tests.Tests1;
import ru.znakarik.otus.tests.Tests2;
import ru.znakarik.otus.tests.Tests3;

import java.util.Arrays;

public class TestRunner {

    public static void main(String[] args) {
        TestRunner.runTests(Tests1.class, Tests2.class, Tests3.class);
    }

    public static void runTests(Class<?>... testClasses) {
        Runner runner = new Runner();
        Arrays.asList(testClasses).forEach(
                testClass -> {
                    runner.setTestClass(testClass);
                    runner.createTestsForClass();
                    runner.startTests();
                    runner.createReporter();
                    runner.getReporter().printAllExceptions();
                }
        );
    }
}
