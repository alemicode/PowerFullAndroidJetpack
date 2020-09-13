package com.example.powerfulljetpack.persistence

import androidx.room.*
import com.example.powerfulljetpack.models.AccountProperties


@Dao
interface AccountPropertiesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountProperties: AccountProperties): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties): Long


    @Query("SELECT * FROM account_properties WHERE pk = :pk")
    fun searchByPk(pk: Int): AccountProperties?

    @Query("SELECT * FROM ACCOUNT_PROPERTIES WHERE email = :email")
    fun searchByEmail(email: String): AccountProperties?


}