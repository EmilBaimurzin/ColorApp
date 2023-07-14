package com.colorful.game.domain.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.colorful.game.databinding.ItemGameBinding

class GameAdapter: RecyclerView.Adapter<GameViewHolder>() {
    var items = mutableListOf<ItemGame>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(items[position])
    }
}