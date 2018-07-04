package com.challenge.hufsy.mystories.screen.main.di;

import com.challenge.hufsy.mystories.screen.main.MainActivityViewModelFactory;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dagger.Module;
import dagger.Provides;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
@Module
public class MainActivityModule {

    @Provides
    protected MainActivityViewModelFactory provideMainActivityViewModelFactory(FirebaseStorage firebaseStorage, FirebaseDatabase firebaseDatabase) {
        return new MainActivityViewModelFactory(firebaseStorage, firebaseDatabase);
    }

}
