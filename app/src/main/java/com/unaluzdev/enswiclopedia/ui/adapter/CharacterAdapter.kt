package com.unaluzdev.enswiclopedia.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaluzdev.enswiclopedia.R
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_ITEM
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_PROGRESS

class CharacterAdapter(
    private val characterList: ArrayList<SWCharacter?>,
    private val loadMoreFunc: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var viewHolder: LoadingViewHolder? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addCharacters(characterList: ArrayList<SWCharacter>) {
        val newItems =
            if (characterList.size > 0) characterList - this.characterList else emptyList()
        if (newItems.isNotEmpty()) {
            val position = this.characterList.size
            this.characterList.addAll(position, newItems)
            this.notifyDataSetChanged()
        }
    }

    fun removeLoadMoreView() {
        viewHolder?.remove()
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
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            (holder as CharacterViewHolder).render(characterList[position]!!)
        } else {
            with(holder as LoadingViewHolder) {
                this.setOnClickListener(loadMoreFunc)
                viewHolder = this
            }
        }
    }

    override fun getItemCount(): Int = characterList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == characterList.size) VIEW_TYPE_PROGRESS else VIEW_TYPE_ITEM
    }

}