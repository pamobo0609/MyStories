package com.challenge.hufsy.mystories.model.repository;

import android.support.annotation.NonNull;

import com.challenge.hufsy.mystories.model.mapper.FirebaseMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class BaseValueEventListener<M, E> implements ValueEventListener {

    private FirebaseMapper<E, M> mapper;
    private FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallback<M> callback;

    BaseValueEventListener(FirebaseMapper<E, M> mapper,
                           FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallback<M> callback) {
        this.mapper = mapper;
        this.callback = callback;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<M> data = mapper.mapList(dataSnapshot);
        callback.onSuccess(data);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        callback.onError(databaseError.toException());
    }

}
