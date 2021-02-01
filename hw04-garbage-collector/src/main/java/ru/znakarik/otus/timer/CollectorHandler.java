package ru.znakarik.otus.timer;

import java.util.ArrayList;
import java.util.List;

public class CollectorHandler {
    private static CollectorHandler INSTANCE;
    private List<Collector> collectors = new ArrayList<>();

    private CollectorHandler() {
    }

    public static CollectorHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CollectorHandler();
        }
        return INSTANCE;
    }

    public Collector createCollector() {
        Collector collector = new Collector();
        collectors.add(collector);
        return collector;
    }

    public List<Collector> getCollectors() {
        return collectors;
    }
}
