package com.challenge.hufsy.mystories;

import android.support.test.runner.AndroidJUnit4;

import com.challenge.hufsy.mystories.app.converter.EPOCHToStringConverter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/6/18.
 * <p>
 */

@RunWith(AndroidJUnit4.class)
public class ConverterInstrumentedTest {

    @Test
    public void badFormatTest() {
        final long date = 1;

        Assert.assertEquals(null, EPOCHToStringConverter.getStringFromEPOCH(date));

    }

    @Test
    public void formatTest() {
        final long date = 1530910572;

        final String formattedDate = EPOCHToStringConverter.getStringFromEPOCH(date);

        Assert.assertNotEquals(null, formattedDate);
    }

}
