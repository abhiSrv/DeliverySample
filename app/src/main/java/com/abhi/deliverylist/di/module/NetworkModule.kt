package com.abhi.deliverylist.di.module


import com.abhi.deliverylist.networking.DeliveriesApi
import com.abhi.deliverylist.utils.sHTTP_LOG_LEVEL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {
    /**/
    val BASE_URL= "https://mock-api-mobile.dev.lalamove.com/"

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = sHTTP_LOG_LEVEL
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(providesInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        val gson = GsonBuilder()
        return gson.create()
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val response: Response
            response =
                chain.proceed(original)
            response
        }
    }

    @Provides
    @Singleton
    fun providesApiRepo(retrofit: Retrofit): DeliveriesApi {
        return retrofit.create(DeliveriesApi::class.java)
    }
}