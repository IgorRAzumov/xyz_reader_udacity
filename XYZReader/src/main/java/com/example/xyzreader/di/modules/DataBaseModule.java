package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.database.IDataBaseService;
import com.example.xyzreader.model.database.realm.RealmDataBaseService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {
    @Singleton
    @Provides
    public IDataBaseService realmCache() {
        return new RealmDataBaseService();
    }
}
