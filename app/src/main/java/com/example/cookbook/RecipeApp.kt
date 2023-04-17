package com.example.cookbook

import android.app.Application
import com.example.cookbook.di.ApplicationComponent
import com.example.cookbook.di.DaggerApplicationComponent

class RecipeApp : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}