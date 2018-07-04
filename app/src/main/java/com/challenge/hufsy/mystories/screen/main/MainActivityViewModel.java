package com.challenge.hufsy.mystories.screen.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.challenge.hufsy.mystories.R;
import com.challenge.hufsy.mystories.app.MyStoriesConstants;
import com.challenge.hufsy.mystories.app.ResExtractor;
import com.challenge.hufsy.mystories.model.Story;
import com.challenge.hufsy.mystories.model.UploadStoryInfo;
import com.challenge.hufsy.mystories.model.repository.FirebaseDatabaseRepository;
import com.challenge.hufsy.mystories.model.repository.StoryRepository;
import com.challenge.hufsy.mystories.screen.viewobject.ViewObject;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class MainActivityViewModel extends ViewModel implements MainActivityContract.ViewModel {

    private final FirebaseStorage firebaseStorage;
    private final FirebaseDatabase firebaseDatabase;

    private String imageAbsolutePath;
    private String fileExtension;
    private String fileName;

    private StoryRepository storyRepository = new StoryRepository();

    private MutableLiveData<ViewObject<List<Story>>> getStoriesLiveData = new MutableLiveData<>();
    private MutableLiveData<ViewObject<Story>> addNewStoryLiveData = new MutableLiveData<>();

    private MutableLiveData<Void> openCameraEvent = new MutableLiveData<>();
    private MutableLiveData<Void> openGalleryEvent = new MutableLiveData<>();
    private MutableLiveData<Story> openStoryEvent = new MutableLiveData<>();

    MainActivityViewModel(FirebaseStorage firebaseStorage, FirebaseDatabase firebaseDatabase) {
        this.firebaseStorage = firebaseStorage;
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        imageAbsolutePath = null;
        fileExtension = null;
        fileName = null;

        storyRepository = null;

        getStoriesLiveData = null;
        addNewStoryLiveData = null;

        openCameraEvent = null;
        openGalleryEvent = null;
        openStoryEvent = null;

    }

    @Override
    public void setImageAbsolutePath(String path) {
        this.imageAbsolutePath = path;
    }

    @Override
    public String getImageAbsolutePath() {
        return imageAbsolutePath;
    }

    @Override
    public MutableLiveData<ViewObject<Story>> getAddNewStoryLiveData() {
        return addNewStoryLiveData;
    }

    @Override
    public void setFileExtension(String s) {
        this.fileExtension = s;
    }

    @Override
    public void setFileName(String imageFileName) {
        this.fileName = imageFileName;
    }

    @Override
    public MutableLiveData<ViewObject<List<Story>>> getStoriesLiveData() {
        if (null == getStoriesLiveData.getValue()) {
            loadStories();
        }

        return getStoriesLiveData;
    }

    @Override
    public MutableLiveData<Void> getOpenCameraEvent() {
        return openCameraEvent;
    }

    @Override
    public MutableLiveData<Void> getOpenGalleryEvent() {
        return openGalleryEvent;
    }

    @Override
    public MutableLiveData<Story> getOpenStoryEvent() {
        return openStoryEvent;
    }

    @Override
    public void onOpenCameraClick() {
        openCameraEvent.setValue(null);
    }

    @Override
    public void onOpenGalleryClick() {
        openGalleryEvent.setValue(null);
    }

    @Override
    public void onStoryClick(Story clickedStory) {
        openStoryEvent.setValue(clickedStory);
    }

    @Override
    public void uploadImageToStorage() {
        addNewStoryLiveData.setValue(ViewObject.loading());

        final StorageReference imgRef = firebaseStorage.getReference().child(MyStoriesConstants.IMAGES_CHILD);
        final StorageReference fileRef = imgRef.child(fileName.concat(".").concat(fileExtension));

        fileRef.putFile(Uri.parse(imageAbsolutePath)).addOnSuccessListener(
                taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {

                    final Story aStory = new Story(taskSnapshot.getMetadata().getName(), uri.toString(), System.currentTimeMillis());
                    writeInfoToDatabase(aStory);
                    addNewStoryLiveData.setValue(ViewObject.success(aStory));

                }).addOnCanceledListener(() -> addNewStoryLiveData.setValue(ViewObject.error(new RuntimeException(ResExtractor.getInstance()
                        .getString(R.string.request_canceled))))))

                .addOnFailureListener(e -> addNewStoryLiveData.setValue(ViewObject.error(e)))

                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    addNewStoryLiveData.setValue(ViewObject.progress((int) progress));
                });

    }

    private void writeInfoToDatabase(Story aStory) {
        final UploadStoryInfo uploadImageInfo = new UploadStoryInfo(aStory.getName(), aStory.getDownloadUrl(), aStory.getDate());

        final String key = firebaseDatabase.getReference(MyStoriesConstants.IMAGES_CHILD).push().getKey();
        firebaseDatabase.getReference(MyStoriesConstants.IMAGES_CHILD).child(key).setValue(uploadImageInfo);

    }

    @Override
    public void loadStories() {
        getStoriesLiveData.setValue(ViewObject.loading());

        storyRepository.addListener(new FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallback<Story>() {
            @Override
            public void onSuccess(List<Story> result) {
                getStoriesLiveData.setValue(ViewObject.success(result));
            }

            @Override
            public void onError(Exception e) {
                getStoriesLiveData.setValue(ViewObject.error(e));
            }
        });

    }

}
