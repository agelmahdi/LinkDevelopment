package com.agelmahdi.linkdevelopment.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agelmahdi.linkdevelopment.databinding.NavDrawerItemBinding
import com.agelmahdi.linkdevelopment.presentation.NavData

class NavAdapter(private var list: MutableList<NavData>) :
    RecyclerView.Adapter<NavAdapter.NavViewHolder>() {

    inner class NavViewHolder(private var binding: NavDrawerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(navData: NavData) {
            binding.tvTitle.text = navData.title
            navData.resId?.let { binding.ivIcon.setImageResource(it) }
            if (navData.isSelected == true) binding.ivSelected.visibility = View.VISIBLE
            else binding.ivSelected.visibility = View.INVISIBLE
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val itemBinding =
            NavDrawerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NavViewHolder(itemBinding)
    }

    init {
        list.first().isSelected = true
    }


    fun update(navData: NavData) {
        list.forEachIndexed { i, it ->
            it.isSelected = it == navData
            notifyItemChanged(i)
        }
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        val navData = list[position]
        holder.bind(navData)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(navData) }
            }
        }
    }

    private var onItemClickListener: ((NavData) -> Unit)? = null

    fun setOnItemClickListener(listener: (NavData) -> Unit) {
        onItemClickListener = listener
    }
}