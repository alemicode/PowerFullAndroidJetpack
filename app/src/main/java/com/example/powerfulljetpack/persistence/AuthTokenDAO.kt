package com.example.powerfulljetpack.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.powerfulljetpack.models.AuthToken


@Dao
interface AuthTokenDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authToken: AuthToken): Long


    @Query("UPDATE auth_token SET token = null where account_pk = :pk")
    fun nullifyToken(pk: Int): Int



}