package com.example.frogapi.Data

import com.example.frogapi.Frog
import retrofit2.http.GET

interface APIService{
    @GET("amphibians")
    suspend fun getFrogs():List<Frog>
}