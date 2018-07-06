package com.challenge.hufsy.mystories.screen.storyviewer.di;

import com.challenge.hufsy.mystories.app.di.holder.BaseComponentHolder;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/6/18.
 * <p>
 */
public class StoryViewerActivityComponentHolder extends BaseComponentHolder<StoryViewerActivityComponent> {
    private static final StoryViewerActivityComponentHolder INSTANCE = new StoryViewerActivityComponentHolder();

    public static StoryViewerActivityComponentHolder getInstance() {
        return INSTANCE;
    }

    private StoryViewerActivityComponentHolder() {
    }
}
