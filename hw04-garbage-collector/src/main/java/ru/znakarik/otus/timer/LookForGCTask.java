package ru.znakarik.otus.timer;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;
import java.util.TimerTask;

public class LookForGCTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("Выполняется таска в потоке " + Thread.currentThread().getName());
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        Collector collector = CollectorHandler.getInstance().createCollector();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                long duration = 0;
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    if (!(info.getGcInfo().getDuration() == duration)) {
                        duration = info.getGcInfo().getDuration();
                        String gcName = info.getGcName();
                        String gcAction = info.getGcAction();
                        String gcCause = info.getGcCause();
                        long startTime = info.getGcInfo().getStartTime();
                        System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                        collector.collect(gcAction, duration);
                    }
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}

