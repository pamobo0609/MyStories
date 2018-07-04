package com.challenge.hufsy.mystories.app.di.holder;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public abstract class BaseComponentHolder<T> implements ComponentHolder<T> {

    private T component;

    @Override
    public void bindComponent(T component) {
        this.component = component;
    }

    @Override
    public void unbindComponent() {
        this.component = null;
    }

    @Override
    public T getComponent() {
        return component;
    }

}
