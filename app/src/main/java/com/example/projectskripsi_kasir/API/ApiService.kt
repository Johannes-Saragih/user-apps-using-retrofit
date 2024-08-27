package com.example.projectskripsi_kasir.API

import com.example.projectskripsi_kasir.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    fun register(@Body user: Register): Call<ApiResponse>

    @POST("login")
    fun login(@Body user: Login): Call<LoginRespone>

    @GET("orders/user/{user_id}")
    fun getUserOrders(@Path("user_id") userId: String): Call<OrderResponse>

}