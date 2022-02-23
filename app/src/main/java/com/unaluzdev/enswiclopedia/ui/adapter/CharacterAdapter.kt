package com.unaluzdev.enswiclopedia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaluzdev.enswiclopedia.R
import com.unaluzdev.enswiclopedia.data.model.CharacterModel

class CharacterAdapter(private val characterList: ArrayList<CharacterModel>) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    fun addCharacters(characterList: ArrayList<CharacterModel>) {
        val nNewItems = characterList.size
        val position = this.characterList.lastIndex
        this.characterList += characterList
        this.notifyItemRangeInserted(position, nNewItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return CharacterViewHolder(
            layoutInflater.inflate(
                R.layout.character_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.render(characterList[position])
    }

    override fun getItemCount(): Int = characterList.size
}