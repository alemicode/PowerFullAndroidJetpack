package com.example.powerfulljetpack.api.main

import androidx.lifecycle.LiveData
import com.example.mvi.main.util.GenericApiResponse
import com.example.powerfulljetpack.api.GenericResponse
import com.example.powerfulljetpack.models.AccountProperties
import retrofit2.http.*

interface OpenApiMainService {

    @GET("account/properties")
    fun getAccountProperties(

        @Header("Authorization") authorization: String
    ): LiveData<GenericApiResponse<AccountProperties>>


    @PUT("account/properties/update")
    @FormUrlEncoded
    fun saveAccountProperties(

        @Header("Authorization") authorization: String,
        @Field("email") email: String,
        @Field("username") username: String
    ): LiveData<GenericApiResponse<GenericResponse>>

    @PUT("account/change_password/")
    @FormUrlEncoded
    fun updatePassword(

        @Header("Authorization") authorization: String,
        @Field("old_password") currentPassword: String,
        @Field("new_password") new_password: String,
        @Field("confirm_new_password") confirmNewPassword: String
    ): LiveData<GenericApiResponse<GenericResponse>>
}