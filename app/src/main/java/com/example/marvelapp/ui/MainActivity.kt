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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.data.MainModel
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.retrofit.ApiService
import com.example.marvelapp.viewmodel.MainViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    private var isGrideMode = false

    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var shrimmerView: ShimmerFrameLayout
    private lateinit var viewModel: MainViewModel

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

        // Hubungin View Modelnya
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecylerView()

//        viewModel.mainData.observe(this){
//            showData(it)
//        }

//        viewModel.isLoading.observe(this){isLoading ->
//            if(isLoading){
//                shrimmerView.startShimmer()
//                Handler(Looper.getMainLooper()).postDelayed({
//                    shrimmerView.stopShimmer()
//                    shrimmerView.visibility = View.GONE
//                }, 5000)
//            }else{
//                shrimmerView.stopShimmer()
//                shrimmerView.visibility = View.GONE
//            }
//        }

        viewModel.mainData.observe(this, Observer { data ->
            data?.let {
                showData(data)
            }
        })


        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if(isLoading){
                    shrimmerView.startShimmer()
                    Handler(Looper.getMainLooper()).postDelayed({
                        shrimmerView.stopShimmer()
                        shrimmerView.visibility = View.GONE
                    }, 7000)
                }else{
                    shrimmerView.stopShimmer()
                    shrimmerView.visibility = View.GONE
                }
            }
        })

        viewModel.init()

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
        }, isGrideMode)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mainAdapter
        }
    }



    private fun printLog(message: String){
        Log.d(TAG, message)
    }

    private fun showData(data: MainModel){
        val results = data.result
        mainAdapter.setData(results)
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
                mainAdapter.setGridMode(false)
            }
            R.id.action_grid -> {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
               mainAdapter.setGridMode(true)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}