package com.unaluzdev.enswiclopedia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaluzdev.enswiclopedia.R
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_ITEM
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_PROGRESS

class CharacterAdapter(private val characterList: ArrayList<SWCharacter?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var viewHolder: LoadingViewHolder? = null

    fun addCharacters(characterList: ArrayList<SWCharacter>) {
        val position = this.characterList.lastIndex
        val nNewItems = characterList.size - this.characterList.size
        this.characterList += characterList - this.characterList
        this.notifyItemRangeInserted(position, nNewItems)
    }

    fun addLoadingView(onClick: () -> Unit) {
        characterList.add(null)
        notifyItemInserted(characterList.lastIndex)
        viewHolder?.setOnClickListener(onClick)
    }

    fun moveLoadView(currentPos: Int, onClick: () -> Unit) {
        if (currentPos in 0..characterList.lastIndex) {
            characterList.removeAt(currentPos)
            notifyItemRemoved(characterList.size)
            addLoadingView(onClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_PROGRESS) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val holder = LoadingViewHolder(
                layoutInflater.inflate(
                    R.layout.load_more,
                    parent,
                    false
                )
            )
            viewHolder = holder
            return holder
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            return CharacterViewHolder(
                layoutInflater.inflate(
                    R.layout.character_list_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        characterList[position]?.let {
            if (holder.itemViewType == VIEW_TYPE_ITEM) {
                (holder as CharacterViewHolder).render(it)
            }
        }
    }

    override fun getItemCount(): Int = characterList.size

    override fun getItemViewType(position: Int): Int {
        return if (characterList[position] == null) VIEW_TYPE_PROGRESS else VIEW_TYPE_ITEM
    }

}