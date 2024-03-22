package com.example.cookbook

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher
import java.lang.reflect.Field

class ViewIdlingResource(
    private val viewMatcher: Matcher<View?>?,
    private val idleMatcher: Matcher<View?>?
) : IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    /**
     * {@inheritDoc}
     */
    override fun isIdleNow(): Boolean {
        val view: View? = getView(viewMatcher)
        val isIdle: Boolean = idleMatcher?.matches(view) ?: false
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    /**
     * {@inheritDoc}
     */
    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }

    /**
     * {@inheritDoc}
     */
    override fun getName(): String? {
        return "$this $viewMatcher"
    }

    /**
     * Tries to find the view associated with the given [<].
     */
    private fun getView(viewMatcher: Matcher<View?>?): View? {
        return try {
            val viewInteraction = onView(viewMatcher)
            val finderField: Field? = viewInteraction.javaClass.getDeclaredField("viewFinder")
            finderField?.isAccessible = true
            val finder = finderField?.get(viewInteraction) as ViewFinder
            finder.view
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        /**
         * Waits for a matching View or throws an error if it's taking too long.
         */
        public fun waitUntilViewIsDisplayed(matcher: Matcher<View?>) {
            val idlingResource: IdlingResource = ViewIdlingResource(matcher, isDisplayed())
            try {
                IdlingRegistry.getInstance().register(idlingResource)
                // First call to onView is to trigger the idler.
                onView(withId(0)).check(doesNotExist())
            } finally {
                IdlingRegistry.getInstance().unregister(idlingResource)
            }
        }
    }
}