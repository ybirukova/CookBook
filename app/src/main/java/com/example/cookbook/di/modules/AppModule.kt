package com.example.cookbook.di.modules

import com.example.cookbook.data.scheduler_provider.AppSchedulerProvider
import com.example.cookbook.data.scheduler_provider.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}