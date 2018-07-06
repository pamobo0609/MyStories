package com.challenge.hufsy.mystories.screen.storyviewer.di;

import com.challenge.hufsy.mystories.app.di.AppComponent;
import com.challenge.hufsy.mystories.app.di.scope.ActivityScope;
import com.challenge.hufsy.mystories.screen.storyviewer.StoryViewerActivity;

import dagger.Component;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/6/18.
 * <p>
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface StoryViewerActivityComponent {

    void inject(StoryViewerActivity activity);

}
