package com.akbarfikri.noteAppBattlepassRaionFirst.data.api

import com.akbarfikri.noteAppBattlepassRaionFirst.data.model.user.User
import com.akbarfikri.noteAppBattlepassRaionFirst.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/register")
    suspend fun signup(@Body user: User): Response<UserResponse>

    @POST("/login")
    suspend fun login(@Body user: User): Response<UserResponse>

}