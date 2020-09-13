package com.example.powerfulljetpack.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.powerfulljetpack.models.AccountProperties
import com.example.powerfulljetpack.models.AuthToken


@Database(entities = [AccountProperties::class, AuthToken::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authTokenDAO(): AuthTokenDAO
    abstract fun accountPropertiesDAO(): AccountPropertiesDAO

    companion object {
        val DATABASE_NAME = "app_db"
    }
}