package com.challenge.hufsy.mystories.model.mapper;

import com.google.firebase.database.DataSnapshot;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public abstract class FirebaseMapper<E, M> implements Mapper<E, M> {

    public M map(DataSnapshot dataSnapshot) {
        E entity = dataSnapshot.getValue(getEntityClass());
        return map(entity);
    }

    public List<M> mapList(DataSnapshot dataSnapshot) {
        final List<M> list = new ArrayList<>();
        for (DataSnapshot item : dataSnapshot.getChildren()) {
            list.add(map(item));
        }
        return list;
    }

    private Class<E> getEntityClass() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) superclass.getActualTypeArguments()[0];
    }

}
