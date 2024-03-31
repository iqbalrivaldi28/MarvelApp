package com.example.marvelapp.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelapp.data.MainModel
import com.example.marvelapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _mainData = MutableLiveData<MainModel>()
    val mainData: LiveData<MainModel> = _mainData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun init(){
        getDataFromApi()
    }

    private fun getDataFromApi(){
        _isLoading.value = true
        ApiService.endpoint.getData()
            .enqueue(object : Callback<MainModel> {
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                       _mainData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "Error: ${t.message.toString()}")
                }
            })
    }


    companion object{
         private const val TAG = "MainViewModel"
    }
}