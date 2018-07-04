package com.challenge.hufsy.mystories.screen.main;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.challenge.hufsy.mystories.BuildConfig;
import com.challenge.hufsy.mystories.R;
import com.challenge.hufsy.mystories.app.MyStoriesConstants;
import com.challenge.hufsy.mystories.app.base.BaseCellDelegateAdapter;
import com.challenge.hufsy.mystories.app.di.AppComponentHolder;
import com.challenge.hufsy.mystories.model.Story;
import com.challenge.hufsy.mystories.screen.base.BaseButterKnifeActivity;
import com.challenge.hufsy.mystories.screen.main.adapter.StoryCellDelegate;
import com.challenge.hufsy.mystories.screen.main.di.DaggerMainActivityComponent;
import com.challenge.hufsy.mystories.screen.main.di.MainActivityComponentHolder;
import com.challenge.hufsy.mystories.screen.storyviewer.StoryViewerActivity;
import com.challenge.hufsy.mystories.screen.storyviewer.StoryViewerActivityContract;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseButterKnifeActivity implements MainActivityContract.View,
        MainActivityContract.Router {

    private static final String[] APP_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int APP_PERMISSIONS_REQUEST_CODE = 99;
    private static final int OPEN_GALLERY_REQUEST_CODE = 98;
    private static final int OPEN_CAMERA_REQUEST_CODE = 97;

    private static final String[] DATA_TYPE = {"image/*", "video/*"};

    @Inject
    protected MainActivityViewModelFactory viewModelFactory;

    @BindView(R.id.swipeToRefresh)
    protected SwipeRefreshLayout swipeToRefresh;

    @BindView(R.id.tvStoriesLabel)
    protected TextView tvStoriesLabel;

    @BindView(R.id.storiesList)
    protected RecyclerView storiesList;

    private MainActivityContract.ViewModel viewModel;
    private BaseCellDelegateAdapter<Story> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDI();

        initStoriesList();

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        viewModel.getOpenCameraEvent().observe(this, aVoid -> routeToCamera());
        viewModel.getOpenGalleryEvent().observe(this, aVoid -> routeToGallery());
        viewModel.getOpenStoryEvent().observe(this, this::routeToOpenStory);

        swipeToRefresh.setOnRefreshListener(() -> viewModel.loadStories());

        viewModel.getStoriesLiveData().observe(this, listViewObject -> {
            if (null == listViewObject) {
                return;
            }

            switch (listViewObject.getStatus()) {
                case LOADING:
                    toggleProgressView(true);
                    break;

                case SUCCESS:
                    toggleProgressView(false);

                    handleResult(listViewObject.getData());

                    break;

                case ERROR:
                    toggleProgressView(false);

                    final Throwable anError = listViewObject.getError();
                    if (null != anError) {
                        showMessage(anError.getLocalizedMessage());
                    }

                    break;
            }
        });

        viewModel.getAddNewStoryLiveData().observe(this, storyViewObject -> {
            if (null == storyViewObject) {
                return;
            }

            switch (storyViewObject.getStatus()) {
                case LOADING:

                    showUploadNotification();
                    break;

                case ERROR:

                    final Throwable anError = storyViewObject.getError();
                    if (null != anError) {
                        showMessage(anError.getLocalizedMessage());
                    }

                    break;

                case SUCCESS:
                    showMessage(R.string.file_uploaded);

                    viewModel.loadStories();

                    break;
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivityComponentHolder.getInstance().unbindComponent();
    }

    @Override
    protected int getContentViewLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case OPEN_GALLERY_REQUEST_CODE:

                    final Uri selectedFile = data.getData();

                    final String absolutePath = getPath(selectedFile);
                    viewModel.setFileName(getFileName(absolutePath));
                    viewModel.setFileExtension(getFileExtension(selectedFile));

                    viewModel.setImageAbsolutePath(selectedFile.toString());

                    viewModel.uploadImageToStorage();

                    break;

                case OPEN_CAMERA_REQUEST_CODE:

                    publishPhotoToGallery();

                    viewModel.uploadImageToStorage();

                    break;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case APP_PERMISSIONS_REQUEST_CODE:

                boolean allPermissionsGiven = false;
                for (int item : grantResults) {
                    if (0 == item) {
                        allPermissionsGiven = true;
                    } else {
                        allPermissionsGiven = false;
                        break;
                    }

                }

                if (allPermissionsGiven) {
                    routeToNewStory();
                } else {
                    showMessage(R.string.all_permissions_are_required);
                }

                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void toggleProgressView(boolean showProgress) {
        swipeToRefresh.setRefreshing(showProgress);
        tvStoriesLabel.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

    @Override
    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            routeToNewStory();

        } else {
            ActivityCompat.requestPermissions(this, APP_PERMISSIONS, APP_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void publishPhotoToGallery() {
        final Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        final File f = new File(viewModel.getImageAbsolutePath());
        final Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void showMessage(@StringRes int stringRes) {
        Snackbar.make(findViewById(android.R.id.content), stringRes, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void routeToNewStory() {
        final Dialog aDialog = new Dialog(this);
        aDialog.setContentView(R.layout.dialog_choose_source);

        final AppCompatButton openCamera = aDialog.findViewById(R.id.buttonOpenCamera);
        openCamera.setOnClickListener(v -> {
            viewModel.onOpenCameraClick();
            aDialog.dismiss();
        });

        final AppCompatButton openGallery = aDialog.findViewById(R.id.buttonOpenGallery);
        openGallery.setOnClickListener(v -> {
            viewModel.onOpenGalleryClick();
            aDialog.dismiss();
        });

        aDialog.show();
        aDialog.getWindow().setAttributes(getCorrectLayoutParams(aDialog));
    }

    @Override
    public void routeToCamera() {
        final Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (null == openCamera.resolveActivity(getPackageManager())) {
            showMessage(R.string.no_camera_installed);
        } else {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                showMessage(R.string.error_while_creating_image_file);
            }

            if (null != photoFile) {
                final Uri uriForFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID.concat(".provider"),
                        photoFile);

                openCamera.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                startActivityForResult(openCamera, OPEN_CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void routeToGallery() {
        final Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGallery.setType("*/*");
        openGallery.putExtra(Intent.EXTRA_MIME_TYPES, DATA_TYPE);

        startActivityForResult(openGallery, OPEN_GALLERY_REQUEST_CODE);
    }

    @Override
    public void routeToOpenStory(Story clickedStory) {
        final Intent openStoryDetail = new Intent(this, StoryViewerActivity.class);
        openStoryDetail.putExtra(StoryViewerActivityContract.STORY_EXTRA_KEY, clickedStory);

        startActivity(openStoryDetail);
    }

    @OnClick(R.id.buttonAddStory)
    void onAddNewStoryClick() {
        requestPermissions();
    }

    private void initDI() {
        MainActivityComponentHolder.getInstance().bindComponent(DaggerMainActivityComponent.builder()
                .appComponent(AppComponentHolder.getInstance().getComponent())
                .build());

        MainActivityComponentHolder.getInstance().getComponent().inject(this);
    }

    private WindowManager.LayoutParams getCorrectLayoutParams(final Dialog aDialog) {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(aDialog.getWindow().getAttributes());

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return params;
    }

    private void initStoriesList() {
        final StoryCellDelegate storyCellDelegate = new StoryCellDelegate();
        storyCellDelegate.setClickListener(((itemView, position, item) -> viewModel.onStoryClick(item)));

        adapter = new BaseCellDelegateAdapter<>();
        adapter.setCellDelegates(storyCellDelegate);

        storiesList.setAdapter(adapter);
        storiesList.setHasFixedSize(true);
        storiesList.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();
    }

    private void handleResult(List<Story> data) {
        if (null == data || data.isEmpty()) {
            showMessage(R.string.there_are_no_stories_yet_add_one);
            tvStoriesLabel.setText(R.string.no_stories_yet);
        } else {
            adapter.setItems(data);
            adapter.notifyDataSetChanged();
        }
    }

    private File createImageFile() throws IOException {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        final String imageFileName = "JPEG_" + timeStamp + "_";

        final File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        final File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        viewModel.setFileName(imageFileName);
        viewModel.setFileExtension(".jpg");

        viewModel.setImageAbsolutePath(Uri.fromFile(image).toString());

        return image;
    }

    private void showUploadNotification() {
        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyStoriesConstants.UPLOAD_CHANNEL_ID);
        builder.setContentTitle(getString(R.string.file_upload))
                .setContentText(getString(R.string.uploading_in_progress))
                .setSmallIcon(R.drawable.insert_photo)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        builder.setProgress(100, 0, true);
        notificationManagerCompat.notify(1, builder.build());

    }

    private String getFileName(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf("/") + 1).split("\\.")[0];
    }

    private String getFileExtension(Uri uri) {
        final ContentResolver contentResolver = getContentResolver();
        final MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public String getPath(Uri uri) {
        final String[] projection = {MediaStore.Video.Media.DATA};
        final Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (null == cursor) {
            return null;
        } else {
            final int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    }


}
