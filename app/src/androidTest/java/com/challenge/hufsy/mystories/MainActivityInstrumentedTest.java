package com.challenge.hufsy.mystories;

import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.challenge.hufsy.mystories.screen.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/6/18.
 * <p>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private static final String CAMERA_PERMISSION = "android.permission.CAMERA";
    private static final String READ_EXT_STORAGE_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String WRITE_EXT_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void grantPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " " + CAMERA_PERMISSION);

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " " + READ_EXT_STORAGE_PERMISSION);

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " " + WRITE_EXT_STORAGE_PERMISSION);
        }
    }

    @Test
    public void openGalleryTest() {
        onView(withId(R.id.buttonAddStory)).perform(click());
        onView(withId(R.id.buttonOpenGallery)).perform(click());
    }

    @Test
    public void openCameraTest() {
        onView(withId(R.id.buttonAddStory)).perform(click());
        onView(withId(R.id.buttonOpenCamera)).perform(click());
    }

    @Test
    public void swipeToRefreshTest() {
        onView(withId(R.id.swipeToRefresh)).perform(swipeDown());
    }

}
