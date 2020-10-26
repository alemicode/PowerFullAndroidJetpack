package com.example.powerfulljetpack.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.models.AuthToken
import com.example.powerfulljetpack.models.BlogPost


@Database(entities = [AccountProperties::class, AuthToken::class, BlogPost::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authTokenDAO(): AuthTokenDAO
    abstract fun accountPropertiesDAO(): AccountPropertiesDAO
    abstract fun getBlogPostDao(): BlogPostDAO

    companion object {
        val DATABASE_NAME = "app_db"
    }
}