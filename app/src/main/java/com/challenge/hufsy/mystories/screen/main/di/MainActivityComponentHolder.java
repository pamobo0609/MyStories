package com.challenge.hufsy.mystories.screen.main.di;

import com.challenge.hufsy.mystories.app.di.holder.BaseComponentHolder;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class MainActivityComponentHolder extends BaseComponentHolder<MainActivityComponent> {
    private static final MainActivityComponentHolder ourInstance = new MainActivityComponentHolder();

    public static MainActivityComponentHolder getInstance() {
        return ourInstance;
    }

    private MainActivityComponentHolder() {
    }
}
