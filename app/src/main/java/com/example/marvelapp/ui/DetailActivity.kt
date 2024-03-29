package com.example.marvelapp.ui

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil titlnye di taro ke action bar
        supportActionBar?.title = intent.getStringExtra("INTENT_TITLE")

        // Navigasi ke Home
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Rubah warna untuk app bar
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))



        // Ambil gambarnya
        Glide.with(this)
            .load(intent.getStringExtra("INTENT_IMAGE"))
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(binding.imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}