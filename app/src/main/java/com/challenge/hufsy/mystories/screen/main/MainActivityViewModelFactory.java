package com.challenge.hufsy.mystories.screen.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final FirebaseStorage storageReference;
    private final FirebaseDatabase databaseReference;

    public MainActivityViewModelFactory(FirebaseStorage storageReference, FirebaseDatabase databaseReference) {
        this.storageReference = storageReference;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(storageReference, databaseReference);
    }
}
