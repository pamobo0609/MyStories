package com.challenge.hufsy.mystories.app.di;

import android.content.Context;

import com.challenge.hufsy.mystories.app.App;
import com.challenge.hufsy.mystories.app.NotificationManager;
import com.challenge.hufsy.mystories.app.di.scope.ActivityScope;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Component;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    FirebaseStorage provideStorageRef();

    FirebaseDatabase provideDatabaseRef();

    Context provideContext();

    void inject(App app);

}
