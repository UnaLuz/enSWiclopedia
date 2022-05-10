package com.unaluzdev.enswiclopedia.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaluzdev.enswiclopedia.R
import com.unaluzdev.enswiclopedia.databinding.HorizontalCardListItemBinding
import com.unaluzdev.enswiclopedia.domain.model.Species
import com.unaluzdev.enswiclopedia.ui.helper.renderImageWith

class SpeciesAdapter(private var speciesList: List<Species>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class SpeciesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HorizontalCardListItemBinding.bind(view)

        fun render(species: Species) {
            with(binding) {
                horizontalCardTextView.text = species.name
                renderImageWith(
                    root.context,
                    horizontalCardImageView,
                    species.imgUrl
                ) { circleCrop() }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SpeciesViewHolder(
            layoutInflater.inflate(
                R.layout.horizontal_card_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as SpeciesViewHolder) {
            val species = speciesList[position]
            render(species)
        }
    }

    override fun getItemCount(): Int = speciesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(species: List<Species>) {
        speciesList = species
        notifyDataSetChanged()
    }
}