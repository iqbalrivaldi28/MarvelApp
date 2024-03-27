package com.example.marvelapp.retrofit

import com.example.marvelapp.data.MainModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {

    @GET("photos")
    fun getPhotos(): Call<List<MainModel>>
}