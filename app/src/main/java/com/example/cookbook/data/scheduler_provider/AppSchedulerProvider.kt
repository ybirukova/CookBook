package com.example.cookbook.data.scheduler_provider

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulerProvider : SchedulerProvider {
    override fun io(): Scheduler = Schedulers.io()
    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
}