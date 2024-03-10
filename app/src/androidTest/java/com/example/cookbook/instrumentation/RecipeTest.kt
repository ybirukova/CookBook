package com.example.cookbook.instrumentation

import com.example.cookbook.ViewIdlingResource.Companion.waitUntilViewIsDisplayed
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookbook.R
import com.example.cookbook.ui.MainActivity
import com.example.cookbook.ui.recycler.RecipeViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipeTest {
    @get:Rule
    var activityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun shouldBeAbleToSearchRecipeSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.et_search_click)).perform(click())
        onView(withId(R.id.et_search)).perform(
            click(),
            typeText("cookie"),
            pressImeActionButton()
        )
        waitUntilViewIsDisplayed(withId(R.id.rv_search_result))
        onView(withId(R.id.rv_search_result))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecipeViewHolder>(0, click())
            )
    }

    @Test
    fun shouldBeAbleToCreateOwnRecipe(){
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.navigation_own_recipes)).perform(click())
        onView(withId(R.id.create_recipe)).perform(click())
        onView(withId(R.id.et_title)).perform(
            click(),
            typeText("cookie")
        )
        onView(withId(R.id.et_add_product)).perform(
            click(),
            typeText("flavor, sugar, milk, nuts, chocolate")
        )
        onView(withId(R.id.et_description)).perform(
            click(),
            typeText("step 1 \nstep 2 \nstep 3"),
            pressBack()
        )
        waitUntilViewIsDisplayed(withId(R.id.button_done))
        onView(withId(R.id.button_done)).perform(
            click()
        )
        onView(withId(R.id.rv_own_recipes)).check(matches(isDisplayed()))
    }
}
