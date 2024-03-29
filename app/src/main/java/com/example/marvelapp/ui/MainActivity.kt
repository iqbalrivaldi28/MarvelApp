package com.example.marvelapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.data.MainModel
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        setupRecylerView()
        getDataFromApi()
    }

    // Add Mainadapater OnListener
    private fun setupRecylerView() {
        mainAdapter = MainAdapter(arrayListOf(), object: MainAdapter.OnAdapterListener{
            override fun onClick(results: MainModel.Result) {
                //Toast.makeText(applicationContext, results.title, Toast.LENGTH_SHORT).show()

                // To Detail Activity
                startActivity(Intent(applicationContext, DetailActivity::class.java)
                    .putExtra("INTENT_TITLE", results.title)
                    .putExtra("INTENT_IMAGE", results.image)
                )

            }

        })
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mainAdapter
        }
    }

    private fun getDataFromApi(){
        binding.progressBar.visibility = View.VISIBLE
        ApiService.endpoint.getData()
            .enqueue(object : Callback<MainModel>{
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful){
                        showData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    printLog(t.toString())
                }

            })
    }

    private fun printLog(message: String){
        Log.d(TAG, message)
    }

    private fun showData(data: MainModel){
        val results = data.result
        mainAdapter.setData(results)
//        for (result in results){
//            printLog("title: ${result.title}")
//        }
    }

}