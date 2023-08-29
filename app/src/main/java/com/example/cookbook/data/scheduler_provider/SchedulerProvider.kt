package com.example.cookbook.data.scheduler_provider

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler
    fun mainThread(): Scheduler
}