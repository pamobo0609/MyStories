package com.challenge.hufsy.mystories.screen.viewobject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class ViewObject<T> {

    @NonNull
    private Status status;
    @Nullable
    private T data;
    @Nullable
    private Throwable error;

    private ViewObject(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> ViewObject<T> loading() {
        return new ViewObject<>(Status.LOADING, null, null);
    }

    public static <T> ViewObject<T> success(@Nullable T data) {
        return new ViewObject<>(Status.SUCCESS, data, null);
    }

    public static <T> ViewObject<T> error(@Nullable Throwable error) {
        return new ViewObject<>(Status.ERROR, null, error);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR,
    }

}
