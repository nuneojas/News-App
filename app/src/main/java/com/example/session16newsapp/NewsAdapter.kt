package com.example.session16newsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.session16newsapp.databinding.ItemNewsBinding

class NewsAdapter(var articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.textTitle.text = item.title
            binding.textDescription.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun updateList( articles: List<Article>){
        this.articles =  articles
        notifyDataSetChanged()
    }
}
