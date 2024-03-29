package com.example.marvelapp

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.data.MainModel

class MainAdapter(val results: ArrayList<MainModel.Result>, val listener: OnAdapterListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(val view : View): RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.textView.text = result.title
        Glide.with(holder.view)
            .load(result.image)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .centerCrop()
            .into(holder.imageView)

        // Adapter Listener
        holder.view.setOnClickListener {
            listener.onClick(result)
        }
    }

    override fun getItemCount() = results.size

    fun setData(data: List<MainModel.Result>){
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    // Adapter click listener
    interface OnAdapterListener{
        fun onClick(results: MainModel.Result)
    }

}