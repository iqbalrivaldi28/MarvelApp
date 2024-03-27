package com.example.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.marvelapp.data.MainModel
import com.example.marvelapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        getDataFromApi()
    }

    private fun getDataFromApi(){
        ApiService.endpoint.getData()
            .enqueue(object : Callback<MainModel>{
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    if (response.isSuccessful){
                        showData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    printLog(t.toString())
                }

            })
    }

    private fun printLog(message: String){
        Log.d(TAG, message)
    }

    private fun showData(data: MainModel){
        val results = data.result
        for (result in results){
            printLog("title: ${result.title}")
        }
    }

}