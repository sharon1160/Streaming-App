package com.example.streamingapp.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.streamingapp.data.network.AuthInterceptor
import com.example.streamingapp.data.network.SoundApiClient
import com.example.streamingapp.data.repository.PaginatedSoundsRepository
import com.example.streamingapp.data.repository.PaginatedSoundsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://freesound.org/apiv2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): SoundApiClient {
        return retrofit.create(SoundApiClient::class.java)
    }


    @Singleton
    @Provides
    fun provideExoplayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    @Singleton
    @Provides
    fun providePaginatedSoundsRepository(
        paginatedSoundsRepositoryImpl: PaginatedSoundsRepositoryImpl
    ): PaginatedSoundsRepository {
        return paginatedSoundsRepositoryImpl
    }
}
