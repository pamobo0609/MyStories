package com.challenge.hufsy.mystories.screen.storyviewer;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.challenge.hufsy.mystories.R;
import com.challenge.hufsy.mystories.model.Story;
import com.challenge.hufsy.mystories.screen.base.BaseButterKnifeActivity;
import com.challenge.hufsy.mystories.screen.view.StoryViewerViewModel;
import com.challenge.hufsy.mystories.screen.view.TouchImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class StoryViewerActivity extends BaseButterKnifeActivity implements StoryViewerActivityContract.View,
        StoryViewerActivityContract.Router {

    @BindView(R.id.storyImage)
    protected TouchImageView storyImage;

    private StoryViewerActivityContract.ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(StoryViewerViewModel.class);
        viewModel.getCloseEvent().observe(this, aVoid -> routeBack());

        final Story extra = getIntent().getParcelableExtra(StoryViewerActivityContract.STORY_EXTRA_KEY);
        if (null == extra) {
            showMessage(R.string.unexpected_error_pls_retry);
            routeBack();
        } else {
            Glide.with(this).load(extra.getDownloadUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.insert_photo_white))
                    .into(storyImage);
        }

    }

    @Override
    protected int getContentViewLayoutRes() {
        return R.layout.activity_story_viewer;
    }

    @Override
    public void showMessage(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void routeBack() {
        finish();
    }

    @OnClick(R.id.buttonCloseStory)
    void onCloseStoryClick() {
        viewModel.onCloseClick();
    }

}
