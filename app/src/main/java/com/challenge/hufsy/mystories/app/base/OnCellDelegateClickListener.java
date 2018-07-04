package com.challenge.hufsy.mystories.app.base;

import android.view.View;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public interface OnCellDelegateClickListener<T> {

    void onCellDelegateClick(View itemView, int position, T item);

}
