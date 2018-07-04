package com.challenge.hufsy.mystories.app;

import android.support.annotation.StringRes;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public final class ResExtractor {
    private static final ResExtractor ourInstance = new ResExtractor();

    private App app;

    public static ResExtractor getInstance() {
        return ourInstance;
    }

    private ResExtractor() {
    }

    public void init(App app) {
        this.app = app;
    }

    public void checkInit() {
        if (null == app) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " must be initialized at "
                    + App.class.getSimpleName());
        }
    }

    public String getString(@StringRes int stringId) {
        checkInit();
        return app.getString(stringId);
    }

    public String getString(@StringRes int stringRes, Object... formatArgs) {
        checkInit();
        return app.getResources().getString(stringRes, formatArgs);
    }

}
