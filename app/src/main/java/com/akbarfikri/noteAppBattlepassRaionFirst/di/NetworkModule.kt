package com.akbarfikri.noteAppBattlepassRaionFirst.di

import com.akbarfikri.noteAppBattlepassRaionFirst.data.api.AuthInterceptor
import com.akbarfikri.noteAppBattlepassRaionFirst.data.api.NotesAPI
import com.akbarfikri.noteAppBattlepassRaionFirst.data.api.UserAPI
import com.akbarfikri.noteAppBattlepassRaionFirst.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Retrofit.Builder = Retrofit
        .Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)

    @Provides
    @Singleton
    fun providesHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI = retrofitBuilder
        .build()
        .create(UserAPI::class.java)

    @Provides
    @Singleton
    fun providesNotesAPI(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): NotesAPI {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(NotesAPI::class.java)
    }

}