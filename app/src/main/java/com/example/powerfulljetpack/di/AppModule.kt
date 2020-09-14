package com.example.powerfulljetpack.di


import android.app.Application;

import androidx.room.Room;
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.powerfulljetpack.R
import com.example.powerfulljetpack.persistence.AccountPropertiesDAO

import com.example.powerfulljetpack.persistence.AppDatabase;
import com.example.powerfulljetpack.persistence.AppDatabase.Companion.DATABASE_NAME
import com.example.powerfulljetpack.persistence.AuthTokenDAO

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthTokenDao(db: AppDatabase): AuthTokenDAO {
        return db.authTokenDAO()
    }

    @Singleton
    @Provides
    fun provideAccountPropertiesDao(db: AppDatabase): AccountPropertiesDAO {
        return db.accountPropertiesDAO()
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.default_image)
            .error(R.drawable.default_image)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

}