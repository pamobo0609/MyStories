package com.challenge.hufsy.mystories.app.base;

import java.util.UUID;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public abstract class BaseCellDelegate<T> implements CellDelegate<T> {

    private final int TYPE = UUID.randomUUID().hashCode();

    @Override
    public int type() {
        return TYPE;
    }

    @Override
    public void bind(BaseViewHolder<T> holder, T item) {
        holder.bind(item);
    }
}
