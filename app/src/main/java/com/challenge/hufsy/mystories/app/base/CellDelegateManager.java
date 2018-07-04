package com.challenge.hufsy.mystories.app.base;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/4/18.
 * <p>
 */
public interface CellDelegateManager<T> {

    void setDelegates(CellDelegate<T>... delegates);

    CellDelegate<T> getDelegate(T item);

    CellDelegate<T> getDelegate(int viewType);

}
