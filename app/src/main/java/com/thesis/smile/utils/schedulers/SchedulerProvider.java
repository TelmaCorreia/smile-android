package com.thesis.smile.utils.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler io();

    Scheduler computation();

    Scheduler trampoline();

    Scheduler newThread();
}
