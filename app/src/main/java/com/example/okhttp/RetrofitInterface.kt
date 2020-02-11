package com.example.okhttp

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {

    @GET("/")
    fun ApiService(): Call<test>
}

