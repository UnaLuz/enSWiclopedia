package com.unaluzdev.enswiclopedia.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.unaluzdev.enswiclopedia.databinding.LoadMoreBinding

class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = LoadMoreBinding.bind(view)

    fun setOnClickListener(onClick: () -> Unit) {
        binding.seeMoreButton.setOnClickListener {
            onClick()
        }
    }

    fun remove() {
        binding.seeMoreButton.setOnClickListener(null)
        binding.seeMoreButton.isClickable = false
        binding.seeMoreButton.visibility = View.GONE
    }

    fun add(onClick: () -> Unit) {
        binding.seeMoreButton.visibility = View.VISIBLE
        binding.seeMoreButton.isClickable = true
        setOnClickListener(onClick)
    }
}