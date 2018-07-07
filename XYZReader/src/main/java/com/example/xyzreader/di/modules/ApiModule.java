package com.example.xyzreader.di.modules;

import com.example.xyzreader.model.api.IApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ApiModule {
    @Singleton
    @Provides
    public IApiService api(Retrofit retrofit) {
        return retrofit.create(IApiService.class);
    }

    @Named("baseUrl")
    @Provides
    public String baseUrl() {
        return "https://go.udacity.com/";
    }

    @Provides
    public Retrofit retrofit(@Named("baseUrl") String baseUrl, OkHttpClient client,
                             GsonConverterFactory gsonConverterFactory,
                             RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

   /* @Provides
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }*/

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    public Gson gson() {
        return new GsonBuilder()
                .create();
    }

}
