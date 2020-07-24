package com.jbhuiyan.projects.vortoyelptest.di

import com.jbhuiyan.projects.vortoyelptest.businesslogic.YelpBusinessApi
import com.jbhuiyan.projects.vortoyelptest.businesslogic.YelpBusinessService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    private val BASE_URL = "https://api.yelp.com/v3/"

    @Provides
    fun providePhotosApi(): YelpBusinessApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(YelpBusinessApi::class.java)
    }

    @Provides
    fun providePhotosService(): YelpBusinessService {
        return YelpBusinessService()
    }

    @Singleton
    fun getUnsafeOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(SupportInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
        return builder.build()
    }

}