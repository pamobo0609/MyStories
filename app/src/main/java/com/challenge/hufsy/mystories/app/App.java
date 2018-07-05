package com.challenge.hufsy.mystories.app;

import android.app.Application;

import com.challenge.hufsy.mystories.app.di.AppComponentHolder;
import com.challenge.hufsy.mystories.app.di.AppModule;
import com.challenge.hufsy.mystories.app.di.DaggerAppComponent;
import com.google.firebase.FirebaseApp;

import javax.inject.Inject;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponentHolder.getInstance().bindComponent(DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build());

        AppComponentHolder.getInstance().getComponent().inject(this);

        ResExtractor.getInstance().init(this);

        FirebaseApp.initializeApp(this);

        NotificationManager.getInstance().init(this);
    }



}
