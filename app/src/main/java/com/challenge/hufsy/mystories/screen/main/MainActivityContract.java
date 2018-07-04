package com.challenge.hufsy.mystories.screen.main;

import android.arch.lifecycle.MutableLiveData;

import com.challenge.hufsy.mystories.model.Story;
import com.challenge.hufsy.mystories.screen.viewobject.ViewObject;

import java.util.List;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public interface MainActivityContract {

    interface View extends com.challenge.hufsy.mystories.screen.base.View {

        void toggleProgressView(boolean showProgress);

        void requestPermissions();

        void publishPhotoToGallery();

    }

    interface ViewModel {

        void setImageAbsolutePath(String path);

        String getImageAbsolutePath();

        MutableLiveData<ViewObject<Story>> getAddNewStoryLiveData();

        MutableLiveData<ViewObject<List<Story>>> getStoriesLiveData();

        MutableLiveData<Void> getOpenCameraEvent();

        MutableLiveData<Void> getOpenGalleryEvent();

        MutableLiveData<Story> getOpenStoryEvent();

        void loadStories();

        void onOpenCameraClick();

        void onOpenGalleryClick();

        void onStoryClick(Story clickedStory);

        void uploadImageToStorage();

        void setFileExtension(String s);

        void setFileName(String imageFileName);
    }

    interface Router {

        void routeToNewStory();

        void routeToCamera();

        void routeToGallery();

        void routeToOpenStory(Story clickedStory);

    }

}
