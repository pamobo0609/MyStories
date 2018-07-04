package com.challenge.hufsy.mystories.app.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/4/18.
 * <p>
 */
public abstract class EPOCHToStringConverter {

    public static String getStringFromEPOCH(long epochDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy @ HH:mm, z", Locale.US);
        return sdf.format(new Date(epochDate));
    }

}
