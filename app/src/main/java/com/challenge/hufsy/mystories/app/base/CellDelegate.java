package com.challenge.hufsy.mystories.app.base;

import android.view.ViewGroup;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public interface CellDelegate<T> {

    boolean is(T item);

    int type();

    BaseViewHolder<T> holder(ViewGroup parent);

    void bind(BaseViewHolder<T> holder, T item);

}
