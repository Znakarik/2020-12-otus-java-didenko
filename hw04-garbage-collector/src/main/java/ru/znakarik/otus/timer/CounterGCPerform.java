package ru.znakarik.otus.timer;

import java.util.ArrayList;
import java.util.List;

public class CounterGCPerform {
    public static void printStatistic() {
        List<Collector> collectorList = CollectorHandler.getInstance().getCollectors();
        List<Long> allYoungInMinList = new ArrayList<>();
        List<Long> allOldInMinList = new ArrayList<>();
        int minutesAmount = collectorList.size();
        // бежим по всем коллекторам
        collectorList.forEach(collector -> {
            long youngInMin = collector.getYoungGen().stream().mapToLong(Long::longValue).sum();
            allYoungInMinList.add(youngInMin);
            long oldInMin = collector.getOldGen().stream().mapToLong(Long::longValue).sum();
            allOldInMinList.add(oldInMin);
        });
        double averageYoungInMun = allYoungInMinList.stream().mapToLong(Long::longValue).sum() / minutesAmount;
        double averageOldInMun = allOldInMinList.stream().mapToLong(Long::longValue).sum() / minutesAmount;
        System.out.printf("""
                ***
                Собиралось %s минут
                Сборка в минуту:
                 YOUNG GEN: %s, OLD GEN: %s
                ***
                """, minutesAmount, averageYoungInMun, averageOldInMun);
    }
}
