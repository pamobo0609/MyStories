package com.challenge.hufsy.mystories.screen.base;

import android.support.annotation.StringRes;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public interface View {

    void showMessage(@StringRes int stringRes);

    void showMessage(String message);

}
