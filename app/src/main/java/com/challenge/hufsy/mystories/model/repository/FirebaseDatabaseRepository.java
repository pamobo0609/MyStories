package com.challenge.hufsy.mystories.model.repository;

import com.challenge.hufsy.mystories.model.mapper.FirebaseMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public abstract class FirebaseDatabaseRepository<M> {

    protected DatabaseReference databaseReference;
    protected FirebaseDatabaseRepositoryCallback<M> firebaseCallback;
    private BaseValueEventListener listener;
    private FirebaseMapper mapper;

    protected abstract String getRootNode();

    public BaseValueEventListener getListener() {
        return listener;
    }

    FirebaseDatabaseRepository(FirebaseMapper mapper) {
        databaseReference = FirebaseDatabase.getInstance().getReference(getRootNode());
        this.mapper = mapper;
    }

    public void addListener(FirebaseDatabaseRepositoryCallback<M> firebaseCallback) {
        this.firebaseCallback = firebaseCallback;
        listener = new BaseValueEventListener(mapper, firebaseCallback);
        databaseReference.addValueEventListener(listener);
    }

    public void removeListener() {
        databaseReference.removeEventListener(listener);
    }

    public interface FirebaseDatabaseRepositoryCallback<T> {
        void onSuccess(List<T> result);

        void onError(Exception e);
    }

}
