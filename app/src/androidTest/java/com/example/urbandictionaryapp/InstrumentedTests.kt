package com.example.urbandictionaryapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.urbandictionaryapp.view.HomeSearchActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class InstrumentedTests {

    @get:Rule var mActivityTestRule: ActivityTestRule<HomeSearchActivity> = ActivityTestRule(HomeSearchActivity::class.java)

    @Test
    fun SearchFindsContent(){
        onView(withId(R.id.word_editText)).perform(typeText("wat"))
        onView(withId(R.id.searchButton)).perform(click())
        assert(getRVcount()>0)

    }

    private fun getRVcount(): Int {
        val recyclerView =
            mActivityTestRule.activity.findViewById(R.id.definition_recyclerView) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }

}