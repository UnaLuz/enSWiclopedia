package com.unaluzdev.enswiclopedia.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unaluzdev.enswiclopedia.databinding.CharacterListItemBinding
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = CharacterListItemBinding.bind(view)

    fun render(swCharacter: SWCharacter) {
        binding.textViewCharacterName.text = swCharacter.name
        Glide.with(binding.root.context)
            .load(swCharacter.imgUrl)
            .centerCrop()
            .into(binding.imageViewCharacter)
    }
}