package com.colorful.game.domain.game

import androidx.recyclerview.widget.DiffUtil

class GameDiffUtilCallback(
    private val oldList: List<ItemGame>,
    private val newList: List<ItemGame>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val isRed = oldList[oldItemPosition].isRed == newList[oldItemPosition].isRed
        val isBlue = oldList[oldItemPosition].isBlue == newList[oldItemPosition].isBlue
        val isGreen = oldList[oldItemPosition].isGreen == newList[oldItemPosition].isGreen
        val isYellow = oldList[oldItemPosition].isYellow == newList[oldItemPosition].isYellow
        val isOwner = oldList[oldItemPosition].owner == newList[oldItemPosition].owner
        return !listOf(isRed, isBlue, isGreen, isYellow, isOwner).contains(false)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val isRed = oldList[oldItemPosition].isRed == newList[oldItemPosition].isRed
        val isBlue = oldList[oldItemPosition].isBlue == newList[oldItemPosition].isBlue
        val isGreen = oldList[oldItemPosition].isGreen == newList[oldItemPosition].isGreen
        val isYellow = oldList[oldItemPosition].isYellow == newList[oldItemPosition].isYellow
        val isOwner = oldList[oldItemPosition].owner == newList[oldItemPosition].owner
        return !listOf(isRed, isBlue, isGreen, isYellow, isOwner).contains(false)
    }
}