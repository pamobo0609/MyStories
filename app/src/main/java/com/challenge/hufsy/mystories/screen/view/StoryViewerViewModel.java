package com.challenge.hufsy.mystories.screen.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.challenge.hufsy.mystories.screen.storyviewer.StoryViewerActivityContract;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/4/18.
 * <p>
 */
public class StoryViewerViewModel extends ViewModel implements StoryViewerActivityContract.ViewModel {

    private MutableLiveData<Void> closeEvent = new MutableLiveData<>();

    public StoryViewerViewModel() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        closeEvent = null;
    }

    @Override
    public MutableLiveData<Void> getCloseEvent() {
        return closeEvent;
    }

    @Override
    public void onCloseClick() {
        closeEvent.setValue(null);
    }
}
