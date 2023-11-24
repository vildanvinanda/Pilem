package com.example.movieaplication.Utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutor {
    private static AppExecutor instance;
    public static AppExecutor getInstance() {
        if (instance == null) {
            instance = new AppExecutor();
        }
        return instance;
    }

    //ini fungsinya buat apa?
    private final ScheduledExecutorService networkID = Executors.newScheduledThreadPool(3);
    public ScheduledExecutorService getNetworkID () {
        return networkID;
    }
}
