package ru.znakarik.otus.starter;

import java.util.List;

public class Reporter {
    private List<Test> tests;

    public Reporter(List<Test> tests) {
        this.tests = tests;
    }

    public void printAllExceptions() {
        this.tests.forEach(test -> {
            int failed = 0;
            int passed = 0;
            if (!test.isPassed()) {
                failed++;
                System.out.println("В тесте " + test.getTest().getName() + " ошибки: ");
                test.getExceptions().forEach(e -> {
                    System.out.println(e.getCause());
                    System.out.println(e.getCause().getStackTrace()[0]);
                });
            } else {
                passed++;
                System.out.println("Тест " + test.getTest().getName() + " успешный");
            }
            System.out.println("Кол-во запущенных тестов в " + test.getClass() + ": " + tests.size());
            System.out.println("Failed: " + failed);
            System.out.println("Passed: " + passed);
            System.out.println("===============");
        });
    }
}
