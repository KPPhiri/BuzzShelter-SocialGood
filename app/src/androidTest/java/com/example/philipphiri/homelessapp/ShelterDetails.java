package com.example.philipphiri.homelessapp;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import junit.framework.Assert;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;

/**
 * Junit test
 */
@RunWith(AndroidJUnit4.class)
public class ShelterDetails{
    private static final int time = 10000;
    @Rule
    public ActivityTestRule<WelcomePageActivity> mActivityRule =
            new ActivityTestRule<>(WelcomePageActivity.class);

    /**
     * validate claim Test
     */
    @Test
    public void validateClaimT() {
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.editTextEmail)).perform(typeText("happy@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("sanrio"),closeSoftKeyboard());
        onView(withId(R.id.okayButton)).perform(click());

        SystemClock.sleep(time);
        onView(withId(R.id.sheltersButton)).perform(click());
        SystemClock.sleep(time);

        onData(anything()).inAdapterView(withId(R.id.shelterListView)).
                atPosition(0).perform(click());
        onView(withId(R.id.claim_number)).perform(typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.claim_button)).perform(click());

        boolean validate = ShelterListActivity.getVerify();
        Assert.assertTrue(validate);
        Espresso.pressBack();
    }

    /**
     * validate claim Test
     */
    @Test
    public void validateClaimF() {
        DatabaseReference userData;

        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.editTextEmail)).perform(typeText("happy@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("sanrio"),closeSoftKeyboard());
        onView(withId(R.id.okayButton)).perform(click());

        SystemClock.sleep(time);
        onView(withId(R.id.sheltersButton)).perform(click());
        SystemClock.sleep(time);

        userData = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference current_user = userData.child(WelcomePageActivity.getCurrentUser());
        current_user.child("ShelterRegistered").setValue("none");
        current_user.child("NumberClaimed").setValue("0");

        onData(anything()).inAdapterView(withId(R.id.shelterListView)).
                atPosition(0).perform(click());
        onView(withId(R.id.claim_number)).perform(typeText("300"), closeSoftKeyboard());
        onView(withId(R.id.claim_button)).perform(click());

        boolean validate = ShelterListActivity.getVerify();
        Assert.assertFalse(validate);
        Espresso.pressBack();
    }
}
