package com.scarike.gp.crawler.util;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskTimer {
    private static final AtomicInteger task_id=new AtomicInteger(0);
    private String message;
    private long begin;

    public TaskTimer(String message) {
        this.message = message;
    }

    public TaskTimer() {
        this.message="task-"+task_id.getAndIncrement();
    }

    public TaskTimer begin(){
        begin=System.currentTimeMillis();
        return this;
    }
    public void end(){
        long end=System.currentTimeMillis();
        System.out.println(message+"耗时"+(end-begin)+"ms");
    }
}
