package com.challenge.hufsy.mystories.screen.main.di;

import com.challenge.hufsy.mystories.app.di.AppComponent;
import com.challenge.hufsy.mystories.app.di.scope.ActivityScope;
import com.challenge.hufsy.mystories.screen.main.MainActivity;

import dagger.Component;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity activity);

}
