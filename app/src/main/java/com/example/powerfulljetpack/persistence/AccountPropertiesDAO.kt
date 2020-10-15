package com.example.powerfulljetpack.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.powerfulljetpack.models.AccountProperties


@Dao
interface AccountPropertiesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountProperties: AccountProperties): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties): Long


    @Query("SELECT * FROM account_properties WHERE pk = :pk")
    fun searchByPk(pk: Int): LiveData<AccountProperties>?

    @Query("SELECT * FROM account_properties WHERE email = :email")
    suspend fun searchByEmail(email: String): AccountProperties?

    @Query("UPDATE ACCOUNT_PROPERTIES SET email = :email, username = :username WHERE pk = :pk")
    fun updateAccountProperties(email: String, username: String, pk: Int)

}