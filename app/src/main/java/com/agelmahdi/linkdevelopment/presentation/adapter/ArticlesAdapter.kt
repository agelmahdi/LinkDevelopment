package com.agelmahdi.linkdevelopment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agelmahdi.linkdevelopment.util.Utils
import com.agelmahdi.linkdevelopment.databinding.FragmentArticlesItemBinding
import com.agelmahdi.linkdevelopment.domain.model.Article
import com.bumptech.glide.Glide

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: FragmentArticlesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article:Article){
            Glide.with(binding.root).load(article.urlToImage).into(binding.ivImage)
            binding.tvTitle.text = article.title
            binding.tvAuthur.text = "by ${article.author}"
            binding.tvDate.text =  Utils.formatDate(article.publishedAt)
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            // use unique properties like id, url, ..etc
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemBinding =
            FragmentArticlesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }


}