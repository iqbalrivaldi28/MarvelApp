package com.example.marvelapp.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.data.MainModel
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.retrofit.ApiService
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var shrimmerView: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(binding.root)

        // Rubah warna untuk app bar
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))

        recyclerView = binding.recyclerView // inisialisasi recyclerView

        shrimmerView = binding.shrimmerView // inisialisasi shrimmer

    }

    override fun onStart() {
        super.onStart()
        setupRecylerView()
        shrimmerView.startShimmer()
        Handler(Looper.getMainLooper()).postDelayed({
            getDataFromApi()
        }, 4000)
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

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mainAdapter
        }
    }

    private fun getDataFromApi(){
        ApiService.endpoint.getData()
            .enqueue(object : Callback<MainModel>{
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    shrimmerView.stopShimmer()
                    shrimmerView.visibility = View.GONE
                    if (response.isSuccessful){
                        showData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    shrimmerView.visibility = View.GONE
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

    // Untuk grid
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_list -> {
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}