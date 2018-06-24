package com.example.xyzreader;

import android.app.Application;


import com.example.xyzreader.di.AppComponent;
import com.example.xyzreader.di.DaggerAppComponent;
import com.example.xyzreader.di.modules.AppModule;

public class App extends Application  {
    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
