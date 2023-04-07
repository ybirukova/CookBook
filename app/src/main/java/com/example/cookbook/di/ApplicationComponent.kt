package com.example.cookbook.di

import android.content.Context
import com.example.cookbook.di.modules.NetworkModule
import com.example.cookbook.di.modules.RepositoryModule
import com.example.cookbook.di.modules.ViewModelModule
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.fragments.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: SearchFragment)

}