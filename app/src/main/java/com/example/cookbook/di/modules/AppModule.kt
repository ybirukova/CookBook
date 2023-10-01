package com.example.cookbook.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("SchedulerIO")
    fun provideSchedulerIO(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named("SchedulerMainThread")
    fun provideSchedulerMainThread(): Scheduler = AndroidSchedulers.mainThread()
}