package com.yuzhihao.learn.config;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuzhihao
 * @since 2023-05-22 15:59:42
 */
public enum ThreadPoolEnum {
    //
    数据处理线程池(new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {

        private final AtomicInteger index = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "handel_data_" + this.index.incrementAndGet());
        }

    })),

    定时任务处理线程池(new ScheduledThreadPoolExecutor(3, new ThreadFactory() {

        private final AtomicInteger index = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "media_scheduled_" + this.index.incrementAndGet());
        }

    })),
    ;

    private final ExecutorService service;

    ThreadPoolEnum(ExecutorService service) {
        this.service = service;
    }

    public void execute(Runnable command) {
        service.execute(command);
    }

    public void shutdown() {
        service.shutdown();
    }

    /**
     * 重复性的执行某个任务
     * @param command
     * @param initialDelay
     * @param period
     * @param unit
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                    long initialDelay,
                                    long period,
                                    TimeUnit unit) {
        return ((ScheduledThreadPoolExecutor) service).scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    /**
     * 在某个时间执行一次任务
     * @param command
     * @param period
     * @param unit
     */
    public void schedule(Runnable command,
                                    long period,
                                    TimeUnit unit) {
        ((ScheduledThreadPoolExecutor) service).schedule(command, period, unit);
    }

}
