package ru.znakarik.otus.timer;

import java.util.ArrayList;
import java.util.Timer;

/*
-Xms512m
-Xmx512m
-Xlog:gc*=info:file=./logs/gc-%p-%t.log
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
-verbose:gc
-XX:+PrintGCDetails
-Xlog:gc*::time

1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        try {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new LookForGCTask(), 0, 60_000);
            System.out.println("=========");
            ArrayList<Object> objects = new ArrayList<>();
            while (true) {
                int i = 200_000;
                int j = 0;
                while (j < i) {
                    objects.add(new Object());
                    j++;
                }
                objects.remove(j / 2);
                j = 0;
                Thread.sleep(3000);
            }
        } catch (OutOfMemoryError error) {
            CounterGCPerform.printStatistic();
            System.exit(0);
        }
    }
}
