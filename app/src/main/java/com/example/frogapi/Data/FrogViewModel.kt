package com.example.frogapi.Data


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.frogapi.Frog
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Retrofit

sealed interface FrogUIState {
    data class Success(val photos: List<Frog>) : FrogUIState
    object Error : FrogUIState
    object Loading : FrogUIState
}
class FrogViewModel :ViewModel(){
    var frogUIState:FrogUIState by mutableStateOf(FrogUIState.Loading)
        private set

    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
    private val frogRepository = NetworkFrogRepo(retrofitService)

    init {
        getFrogs()
    }
    fun getFrogs(){
        viewModelScope.launch {
            frogUIState = try {
                FrogUIState.Success(frogRepository.getFrogs())
            }
            catch (e:HttpException){
                FrogUIState.Error
            }
        }
    }
    companion object{
        val Factory:ViewModelProvider.Factory = viewModelFactory{
            initializer {
                FrogViewModel()
            }
        }
    }
}