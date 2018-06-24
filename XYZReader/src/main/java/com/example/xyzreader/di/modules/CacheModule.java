package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.cache.ICache;
import com.example.xyzreader.model.cache.RealmCache;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
    @Provides
    public ICache realmCache() {
        return new RealmCache();
    }
}
