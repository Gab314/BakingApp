package com.example.gabriel.bakingapp.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.gabriel.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.fragments_recipes_recycler_view),
                        withParent(allOf(withId(R.id.fragment_recipes_Linear_Layout),
                                withParent(withId(R.id.container)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction listView = onView(
                allOf(withId(R.id.ingredient_row_ListView),
                        childAtPosition(
                                allOf(withId(R.id.fragment__detailed_recipes_Linear_Layout),
                                        childAtPosition(
                                                withId(R.id.container_detail),
                                                0)),
                                0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.fragments_detailed_recipes_recycler_view),
                        childAtPosition(
                                allOf(withId(R.id.fragment__detailed_recipes_Linear_Layout),
                                        childAtPosition(
                                                withId(R.id.container_detail),
                                                0)),
                                1),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.fragments_detailed_recipes_recycler_view),
                        withParent(allOf(withId(R.id.fragment__detailed_recipes_Linear_Layout),
                                withParent(withId(R.id.container_detail)))),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));


        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.exo_pause), withContentDescription("Pause"), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.step_TextView), withText("Recipe Introduction"),
                        childAtPosition(
                                allOf(withId(R.id.step_by_step_RelativeLayout),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Recipe Introduction")));


        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.step_next_btn), withText("Next"),
                        withParent(allOf(withId(R.id.step_by_step_RelativeLayout),
                                withParent(withId(R.id.container)))),
                        isDisplayed()));
        appCompatButton.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
