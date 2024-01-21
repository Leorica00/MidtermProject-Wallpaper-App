package com.example.shualeduri.di

import android.content.Context
import com.example.shualeduri.BuildConfig
import com.example.shualeduri.data.common.response_handler.HandleAuthResponse
import com.example.shualeduri.data.common.response_handler.HandleResponse
import com.example.shualeduri.data.repository.AuthRepositoryImpl
import com.example.shualeduri.data.service.WallpaperApiService
import com.example.shualeduri.domain.repository.AuthRepository
import com.example.shualeduri.presentation.util.WallpaperUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        )
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Provides
    @Singleton
    fun provideHandleAuthResponse(): HandleAuthResponse {
        return HandleAuthResponse()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideWallpaperApiService(retrofit: Retrofit): WallpaperApiService {
        return retrofit.create(WallpaperApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(handleAuthResponse: HandleAuthResponse): AuthRepository = AuthRepositoryImpl(
        auth = Firebase.auth,
        handleAuthResponse = handleAuthResponse
    )

    @Provides
    fun provideWallpaperUtil(@ApplicationContext context: Context): WallpaperUtil {
        return WallpaperUtil(context)
    }
}