package com.challenge.hufsy.mystories.app.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/4/18.
 * <p>
 */
public class BaseCellDelegateAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private static final int INDEX_INVALID = -1;

    private final CellDelegateManager<T> cellDelegateManager;
    private final List<T> items;

    public BaseCellDelegateAdapter() {
        this.cellDelegateManager = new BaseCellDelegateManager<>();
        this.items = new ArrayList<>();
    }

    //region RecyclerView.Adapter section
    @Override
    public int getItemViewType(int position) {
        final T item = getItem(position);
        return cellDelegateManager.getDelegate(item).type();
    }

    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return cellDelegateManager.getDelegate(viewType).holder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        final T item = getItem(position);
        cellDelegateManager.getDelegate(item).bind(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    //endregion

    //region CellDelegateManager section
    @SafeVarargs
    public final void setCellDelegates(CellDelegate<T>... cellDelegates) {
        cellDelegateManager.setDelegates(cellDelegates);
    }
    //endregion

    //region Items section
    @Nullable
    public synchronized T getItem(int position) {
        T item = null;
        try {
            item = items.get(position);
        } catch (IndexOutOfBoundsException e) {
            // Do nothing.
        }
        return item;
    }

    public synchronized Collection<T> getItems() {
        return items;
    }

    public synchronized void setItem(int position, T item) {
        if (item == null) {
            return;
        }

        if (items.size() > position) {
            items.set(position, item);
            notifyItemChanged(position);
        } else {
            items.add(item);
            final int positionInsert = items.size() - 1;
            notifyItemInserted(positionInsert);
        }
    }

    public synchronized void setItems(Collection<T> items) {
        if (items == null) {
            return;
        }
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public synchronized void addItems(Collection<T> items) {
        if (items == null) {
            return;
        }
        final int positionStart = this.items.size();
        final int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public synchronized void addItems(int index, Collection<T> items) {
        if (items == null) {
            return;
        }
        this.items.addAll(index, items);
        notifyDataSetChanged();
    }

    public synchronized void removeItem(T item) {
        if (item == null) {
            return;
        }
        final int removeIndex = items.indexOf(item);
        if (removeIndex == INDEX_INVALID) {
            return;
        }
        items.remove(removeIndex);
        notifyItemRemoved(removeIndex);
    }

}
