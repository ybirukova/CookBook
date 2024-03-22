package com.example.cookbook

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    override fun matchesSafely(item: Root): Boolean {
        val type = item.windowLayoutParams.get().type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken = item.decorView.windowToken
            val appToken = item.decorView.applicationWindowToken
            return windowToken == appToken
        }
        return false
    }
}