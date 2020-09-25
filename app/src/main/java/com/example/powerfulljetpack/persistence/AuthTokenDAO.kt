package com.example.powerfulljetpack.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.powerfulljetpack.models.AuthToken


@Dao
interface AuthTokenDAO {


    //for someActions loke LogIn
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authToken: AuthToken): Long


    //for some actions like LogOut
    @Query("UPDATE auth_token SET token = null where account_pk = :pk")
    fun nullifyToken(pk: Int): Int

}