package com.challenge.hufsy.mystories.app.di;

import android.content.Context;

import com.challenge.hufsy.mystories.app.App;
import com.challenge.hufsy.mystories.app.NotificationManager;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    protected Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    protected FirebaseStorage provideStorageRef() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    @Singleton
    protected FirebaseDatabase provideDatabaseRef() {
        return FirebaseDatabase.getInstance();
    }

}
