package com.challenge.hufsy.mystories.screen.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public abstract class BaseButterKnifeActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @LayoutRes
    protected abstract int getContentViewLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutRes() != 0) {
            setContentView(getContentViewLayoutRes());
        } else {
            throw new RuntimeException(
                    "It's needed to return correct layout resource " +
                            "in getContentViewLayoutRes() method"
            );
        }
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}
