package com.example.xyzreader;

import android.app.Application;

import com.example.xyzreader.di.AppComponent;
import com.example.xyzreader.di.modules.AppModule;

import io.realm.Realm;

public class App extends Application {
    private AppComponent appComponent;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
