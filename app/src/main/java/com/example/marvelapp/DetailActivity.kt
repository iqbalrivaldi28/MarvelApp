package com.example.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageView: ImageView = findViewById(R.id.imageView)

        // Ambil titlnye di taro ke action bar
        supportActionBar?.title = intent.getStringExtra("INTENT_TITLE")

        // Ambil gambarnya
        Glide.with(this)
            .load(intent.getStringExtra("INTENT_IMAGE"))
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(imageView)
    }
}