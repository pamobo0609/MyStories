package com.challenge.hufsy.mystories.screen.storyviewer;

import android.arch.lifecycle.MutableLiveData;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/4/18.
 * <p>
 */
public interface StoryViewerActivityContract {

    String STORY_EXTRA_KEY = "storyExtra";

    interface View extends com.challenge.hufsy.mystories.screen.base.View {

    }

    interface ViewModel {

        MutableLiveData<Void> getCloseEvent();

        void onCloseClick();

    }

    interface Router {

        void routeBack();

    }

}
