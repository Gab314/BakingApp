package com.example.gabriel.bakingapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.gabriel.bakingapp.activities.StepByStepActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)


public class RecipeTest {
    @Rule
    public ActivityTestRule<StepByStepActivity> mActivityTestRule =
            new ActivityTestRule<>(StepByStepActivity.class);

        @Test
        public void RecipesAcct(){
            onView((withId(R.id.step_TextView))).check(matches(withText("NOT AVAILABLE")));
        }
}
