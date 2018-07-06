package com.challenge.hufsy.mystories;

import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockApplication;

import com.challenge.hufsy.mystories.app.ResExtractor;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/6/18.
 * <p>
 */
@RunWith(AndroidJUnit4.class)
public class ResExtractorInstrumentedTest {

    @Before
    public void init() {
        final MockApplication mockApplication = new MockApplication();

        ResExtractor.getInstance().init(mockApplication);
    }

    @Test
    public void getStringTest() {
        final String result = ResExtractor.getInstance().getString(R.string.app_name);

        Assert.assertEquals("My Stories", result);
    }

    @Test
    public void getStringWithArgsTest() {
        final String result = ResExtractor.getInstance().getString(R.string.test_with_args, "Hufsy");

        Assert.assertEquals("Hello Huhfsy", result);
    }


}
