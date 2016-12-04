package uco374386.movio2.pv256.fi.muni.cz.filmovarka;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbContract;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumentation test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private boolean tablet = false;

    @org.junit.Before
    public void setUp() throws Exception {
        tablet = mActivityRule.getActivity().tablet;
        InstrumentationRegistry.getTargetContext().getContentResolver().delete(
                MovieDbContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
    }

    @Test
    public void testNavigation() throws InterruptedException {
        //download movies
        Thread.sleep(5000);
        //skip category name item
        onView(withId(R.id.rvMovies))
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        fail("recycler view doesnt show loaded movie");
                    }
                })
                .perform(actionOnItemAtPosition(1, click()));
        if(!tablet)
            Espresso.pressBack();
        //show saved
        onView(withId(R.id.saved))
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        fail("saved switch button missing");
                    }
                })
                .perform(click());
        onView(withId(R.id.rvMovies))
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        fail("recycler view doesnt show that there is not saved movies");
                    }
                })
                .perform(actionOnItemAtPosition(0, click()));
        assertTrue(true);
    }

    @Test
    public void saveMovie() throws InterruptedException {

        //download movies
        Thread.sleep(5000);
        //skip category name item
        onView(withId(R.id.rvMovies))
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        fail("recycler view doesnt show loaded movie");
                    }
                })
                .perform(actionOnItemAtPosition(1, click()));
        //save movie
        onView(withId(R.id.fab))
                .perform(click());
        if(!tablet)
            Espresso.pressBack();
        //show saved
        onView(withId(R.id.saved))
                .perform(click());
        //load DB
        Thread.sleep(1000);
        onView(withId(R.id.rvMovies))
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        fail("recycler view doesnt show saved movie");
                    }
                })
                .perform(actionOnItemAtPosition(0, click()));
        //unsave movie
        onView(withId(R.id.fab))
                .perform(click());
        if(!tablet)
            Espresso.pressBack();
        //load DB
        Thread.sleep(1000);
        try {
            onView(withId(R.id.rvMovies))
                    .perform(actionOnItemAtPosition(1, click()));
            fail("recycler view show UNsaved movie");
        } catch (Exception e) {
            //ok
        }
        //show from net
        onView(withId(R.id.saved))
                .perform(click());
        //download movies
        Thread.sleep(5000);
        onView(withId(R.id.rvMovies))
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        fail("recycler view doesnt show loaded movie");
                    }
                })
                .perform(actionOnItemAtPosition(1, click()));
        if(!tablet)
            Espresso.pressBack();

        assertTrue(true);
    }
}
