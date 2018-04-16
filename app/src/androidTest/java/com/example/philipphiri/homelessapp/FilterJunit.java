package com.example.philipphiri.homelessapp;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import android.widget.ListView;
import junit.framework.Assert;

/**
 * Junit for gender filtering
 *
 * Created by yuri_ on 4/8/2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterJunit {
    private String[] wArray;
    private String[] mArray;
    @Rule
    public ActivityTestRule<ShelterListActivity> mActivityRule = new ActivityTestRule<>(ShelterListActivity.class);

    @Before
    public void initWomenShelterList() {
        wArray = new String[3];
        wArray[0] = "My Sister's House";
        wArray[1] = "The Atlanta Day Center for Women & Children";
        wArray[2] = "Eden Village ";

    }

    @Before
    public void initMenShelterList() {
        mArray = new String[3];
        mArray[0] = "The Shepherd's Inn";
        mArray[1] = "Fuqua Hall";
        mArray[2] = "Gateway Center";

    }

    @Test
    public void checkedWomen() {
        onView(withId(R.id.filterSpinner)).perform(click());
        onView(withText("Gender")).perform(click());
        onView(withId(R.id.female)).perform(click());
        onView(withId(R.id.filterButton)).perform(click());
        int numItems = ((ListView) mActivityRule.getActivity().findViewById(R.id.shelterListView)).getAdapter().getCount();
        Assert.assertEquals(3,numItems);
        for (int i = 0; i < numItems; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.shelterListView)).atPosition(i)
                    .check(matches(isDisplayed()));
        }
        for (int i = 0; i < numItems; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.shelterListView)).atPosition(i)
                    .onChildView(withId(R.id.textViewName)).check(matches(withText(wArray[i])));
        }
    }

    @Test
    public void checkedMen() {
        onView(withId(R.id.filterSpinner)).perform(click());
        onView(withText("Gender")).perform(click());
        onView(withId(R.id.male)).perform(click());
        onView(withId(R.id.filterButton)).perform(click());
        int numItems = ((ListView) mActivityRule.getActivity().findViewById(R.id.shelterListView)).getAdapter().getCount();
        Assert.assertEquals(3,numItems);
        for (int i = 0; i < numItems; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.shelterListView)).atPosition(i)
                    .check(matches(isDisplayed()));
        }
        for (int i = 0; i < numItems; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.shelterListView)).atPosition(i)
                    .onChildView(withId(R.id.textViewName)).check(matches(withText(mArray[i])));
        }
    }


}