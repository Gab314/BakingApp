package com.example.gabriel.bakingapp;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.gabriel.bakingapp.Adapters.RecyclerViewMatcher;
import com.example.gabriel.bakingapp.Utils.RecipeCards;
import com.example.gabriel.bakingapp.activities.StepByStepActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.gabriel.bakingapp.R.id.withText;


@RunWith(AndroidJUnit4.class)


public class RecipeTest {
    @Rule
    public ActivityTestRule<StepByStepActivity> mActivityTestRule =
            new ActivityTestRule<>(StepByStepActivity.class);


    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public void RecipesChecker(){
        onView(withRecyclerView(R.id.fragments_recipes_recycler_view).atPosition(2))
                .check(matches(hasDescendant(withText("Yellow Cake"))));
        onView(withRecyclerView(R.id.fragments_recipes_recycler_view).atPosition(3)).perform(click());
    }
    }



