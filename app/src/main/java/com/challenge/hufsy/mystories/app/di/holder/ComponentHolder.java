package com.challenge.hufsy.mystories.app.di.holder;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public interface ComponentHolder<T> {

    void bindComponent(T component);

    void unbindComponent();

    T getComponent();

}
