package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.database.IDataBaseService;
import com.example.xyzreader.model.database.realm.RealmDataBaseService;
import com.example.xyzreader.model.entity.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;
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
