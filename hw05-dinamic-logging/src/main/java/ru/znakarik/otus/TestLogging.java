package ru.znakarik.otus;

import ru.znakarik.otus.agent.Log;

public class TestLogging {

    private int field;

    @Log
    public void method(String param1, Integer param2) {
        System.out.println("Hello!!!!");
    }

    @Log
    public void method2(String param1) {
        System.out.println("Hello from north!!!!");
    }
}
