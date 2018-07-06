package com.challenge.hufsy.mystories.screen.storyviewer;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.challenge.hufsy.mystories.R;
import com.challenge.hufsy.mystories.app.FileUtil;
import com.challenge.hufsy.mystories.app.di.AppComponentHolder;
import com.challenge.hufsy.mystories.model.Story;
import com.challenge.hufsy.mystories.screen.base.BaseButterKnifeActivity;
import com.challenge.hufsy.mystories.screen.storyviewer.di.DaggerStoryViewerActivityComponent;
import com.challenge.hufsy.mystories.screen.storyviewer.di.StoryViewerActivityComponentHolder;
import com.challenge.hufsy.mystories.screen.view.StoryViewerViewModel;
import com.challenge.hufsy.mystories.screen.view.TouchImageView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class StoryViewerActivity extends BaseButterKnifeActivity implements StoryViewerActivityContract.View,
        StoryViewerActivityContract.Router {

    @BindView(R.id.storyImage)
    protected TouchImageView storyImage;

    @BindView(R.id.videoPlayer)
    protected PlayerView videoPlayer;

    @Inject
    protected FileUtil fileUtil;

    private SimpleExoPlayer player;

    private StoryViewerActivityContract.ViewModel viewModel;

    private Story extra;
    private long playbackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StoryViewerActivityComponentHolder.getInstance().bindComponent(DaggerStoryViewerActivityComponent.builder()
                .appComponent(AppComponentHolder.getInstance().getComponent()).build());

        StoryViewerActivityComponentHolder.getInstance().getComponent().inject(this);

        viewModel = ViewModelProviders.of(this).get(StoryViewerViewModel.class);
        viewModel.getCloseEvent().observe(this, aVoid -> routeBack());

        extra = getIntent().getParcelableExtra(StoryViewerActivityContract.STORY_EXTRA_KEY);
        if (null == extra) {
            showMessage(R.string.unexpected_error_pls_retry);
            routeBack();
        } else {

            if (fileUtil.isAVideo(extra.getName())) {

                storyImage.setVisibility(View.GONE);
                videoPlayer.setVisibility(View.VISIBLE);

            } else {

                storyImage.setVisibility(View.VISIBLE);
                videoPlayer.setVisibility(View.GONE);

                Glide.with(this).load(extra.getDownloadUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.insert_photo_white))
                        .into(storyImage);
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || player == null)) {
            initPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StoryViewerActivityComponentHolder.getInstance().unbindComponent();
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

    private void initPlayer() {
        final MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(this, "exoplayer-codelab")).
                createMediaSource(Uri.parse(extra.getDownloadUrl()));

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        player.seekTo(playbackPosition);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

        videoPlayer.setPlayer(player);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

}
