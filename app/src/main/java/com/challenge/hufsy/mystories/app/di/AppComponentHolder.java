package com.challenge.hufsy.mystories.app.di;

import com.challenge.hufsy.mystories.app.di.holder.BaseComponentHolder;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public final class AppComponentHolder extends BaseComponentHolder<AppComponent> {
    private static final AppComponentHolder ourInstance = new AppComponentHolder();

    public static AppComponentHolder getInstance() {
        return ourInstance;
    }

    private AppComponentHolder() {
    }
}
