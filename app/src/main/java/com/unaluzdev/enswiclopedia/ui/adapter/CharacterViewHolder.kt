package com.unaluzdev.enswiclopedia.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.unaluzdev.enswiclopedia.data.model.CharacterModel
import com.unaluzdev.enswiclopedia.databinding.CharacterListItemBinding

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = CharacterListItemBinding.bind(view)

    fun render(characterModel: CharacterModel) {
        binding.textViewCharacterName.text = characterModel.name
    }
}