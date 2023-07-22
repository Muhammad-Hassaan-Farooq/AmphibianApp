package com.example.frogapi.Data

import com.example.frogapi.Frog

interface FrogRepository{
    suspend fun getFrogs():List<Frog>

}
class NetworkFrogRepo(
    val apiservice: APIService
):FrogRepository{
    override suspend fun getFrogs(): List<Frog>  = apiservice.getFrogs()
}