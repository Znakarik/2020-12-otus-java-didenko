package ru.znakarik.otus.timer;

import java.util.HashSet;
import java.util.Set;

/**
 * Тут собираются все коллекторы, запущенные в {@link LookForGCTask}
 */
public class Collector {
    private Set<Long> youngGen = new HashSet<>();
    private Set<Long> oldGen = new HashSet<>();

    public void collect(String action, long mills) {
        switch (action) {
            case "end of major GC" -> oldGen.add(mills);
            case "end of minor GC" -> youngGen.add(mills);
        }
    }

    public Set<Long> getYoungGen() {
        return youngGen;
    }

    public Set<Long> getOldGen() {
        return oldGen;
    }
}
