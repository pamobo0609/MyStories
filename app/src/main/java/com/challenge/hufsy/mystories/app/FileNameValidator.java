package com.challenge.hufsy.mystories.app;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/5/18.
 * <p>
 */
public class FileNameValidator {

    private static final String VIDEO_PATTERN = "([^\\s]+(\\.(?i)(3gp|mp4|mkv))$)";

    private Pattern pattern;

    public FileNameValidator() {
        this.pattern = Pattern.compile(VIDEO_PATTERN);
    }

    public boolean isAVideo(@NonNull String fileName) {

        final Matcher matcher = pattern.matcher(fileName);

        return matcher.matches();
    }

}
