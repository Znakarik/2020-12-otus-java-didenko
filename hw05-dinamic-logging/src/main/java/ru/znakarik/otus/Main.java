package ru.znakarik.otus;

public class Main {

    public static void main(String[] args) {
        TestLogging testLogging = new TestLogging();
        testLogging.method2("hello again");
        Calculator calculator = new Calculator();
        calculator.calculation(1);
        calculator.calculation(2, 4);
        calculator.calculation(2, 466, "String");
    }
}
