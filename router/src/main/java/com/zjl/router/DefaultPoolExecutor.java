package com.zjl.router;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultPoolExecutor {

    private final ExecutorService executorService;

    private DefaultPoolExecutor() {
        executorService = Executors.newSingleThreadExecutor();
    }

    private static class InnerHolder {
        private static final DefaultPoolExecutor instance = new DefaultPoolExecutor();
    }

    public static DefaultPoolExecutor get() {
        return InnerHolder.instance;
    }

    public void execute(Runnable runnable){
        executorService.execute(runnable);
    }
}
