package com.colorful.game.domain.game

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.colorful.game.R
import com.colorful.game.databinding.ItemGameBinding

class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemGame) {
        val backGround = when (item.owner) {
            Character.GREEN -> R.drawable.bg_green_cell
            Character.YELLOW -> R.drawable.bg_yellow_cell
            Character.RED -> R.drawable.bg_red_cell
            Character.BLUE -> R.drawable.bg_blue_cell
            else -> R.drawable.bg_item
        }
        binding.root.setBackgroundResource(backGround)
        binding.imgRed.isVisible = item.isRed
        binding.imgGreen.isVisible = item.isGreen
        binding.imgYellow.isVisible = item.isYellow
        binding.imgBlue.isVisible = item.isBlue
        binding.imgBulletLeft.isVisible = item.hasBulletLeft
        binding.imgBulletRight.isVisible = item.hasBulletRight
    }
}