package com.challenge.hufsy.mystories.app.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/4/18.
 * <p>
 */
public class BaseCellDelegateManager<T> implements CellDelegateManager<T> {
    private final List<CellDelegate<T>> delegates;

    public BaseCellDelegateManager() {
        delegates = new ArrayList<>();
    }

    @SafeVarargs
    @Override
    public final void setDelegates(CellDelegate<T>... delegates) {
        this.delegates.clear();
        this.delegates.addAll(Arrays.asList(delegates));
    }

    @Override
    public CellDelegate<T> getDelegate(T item) {
        for (CellDelegate<T> delegate : delegates) {
            if (delegate.is(item)) {
                return delegate;
            }
        }
        throw new NullPointerException();
    }

    @Override
    public CellDelegate<T> getDelegate(int viewType) {
        for (CellDelegate<T> delegate : delegates) {
            if (delegate.type() == viewType) {
                return delegate;
            }
        }
        throw new NullPointerException();
    }
}
