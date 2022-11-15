package com.js.uistatepractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.js.uistatepractice.databinding.AdapterNewsBinding

class NewsAdapter(private val itemClickListener: (String) -> Unit) : ListAdapter<String, NewsAdapter.NewsViewHolder>(diff) {

  inner class NewsViewHolder(private val binding: AdapterNewsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String) {

      binding.tvNewsTitle.text = data

      itemView.setOnClickListener {
        itemClickListener(data)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NewsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.adapter_news, parent, false))

  override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
    holder.bind(currentList[position])
  }

  companion object {
    val diff = object : DiffUtil.ItemCallback<String>() {
      override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
      }
    }
  }
}