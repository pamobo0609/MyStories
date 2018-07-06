package com.challenge.hufsy.mystories;

import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;

import com.challenge.hufsy.mystories.app.FileUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/6/18.
 * <p>
 */
@RunWith(AndroidJUnit4.class)
public class FileUtilInstrumentedTest {

    private static final String[] TO_CHECK =

            {
                    "a.mkv", "a.mp4", "a.3gp",
                    "..mkv", "..mp4", "..3gp",
                    "a.MKV", "a.MP4", "a.3GP",
                    "a.MkV", "a.Mp4", "a.3gP",
                    "mkv.mkv", "mp4.mp4", "3gp.3gp"
            };

    private FileUtil fileUtil;

    @Before
    public void init() {
        this.fileUtil = new FileUtil(new MockContext());
    }

    @Test
    public void fileNameInputsTest() {
        for (String test : TO_CHECK) {
            final Boolean isValid = fileUtil.isAVideo(test);
            Assert.assertEquals(true, isValid);
        }
    }

    @Test
    public void createFileTest() {

        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        final String imageFileName = "JPEG_" + timeStamp + "_";

        File newFile = null;
        try {
            newFile = fileUtil.createImageFile(imageFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotEquals(null, newFile);
    }

    @Test
    public void getNullFileNameTest() {
        final String result = fileUtil.getFileName("");
        Assert.assertEquals(null, result);
    }

    @Test
    public void getFileNameTest() {
        final String result = fileUtil.getFileName("/path/to/image/image.jpg");
        Assert.assertEquals("jpg", result);
    }

    @Test
    public void getFileExtensionTest() {
        final String result = fileUtil.getFileExtension(null);

        Assert.assertEquals(null, result);
    }

    @Test
    public void getPathTest() {
        final String result = fileUtil.getPath(null);

        Assert.assertEquals(null, result);
    }

}
