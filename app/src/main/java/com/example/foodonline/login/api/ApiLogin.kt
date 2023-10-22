package com.example.foodonline.login.api


import com.example.foodonline.login.model.Accound
import com.example.foodonline.login.model.SignIn
import com.example.foodonline.login.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiLogin {
    companion object{
        var retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-231-122-229.ap-northeast-1.compute.amazonaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val  apiLogin = retrofit.create(ApiLogin::class.java)

    }
    @GET("login")
    fun getUser(@Query ("username") username : String,
                @Query ("password") password : String): Call<SignIn>
    @POST("registration")
    fun signup(@Body account: Accound): Call<SignIn>
    @POST("login")
    fun login(@Body user: User): Call<SignIn>
}