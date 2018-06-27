package com.example.xyzreader.di.modules;

import com.example.xyzreader.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public App app() {
        return app;
    }
}
