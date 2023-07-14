package com.colorful.game.domain.game

class GameRepository {
    fun initList(): List<ItemGame> {
        val list = mutableListOf<ItemGame>()
        repeat(54) {
            list.add(ItemGame(false, false, false, false, null))
        }
        list[0].isGreen = true
        list[8].isYellow = true
        list[45].isRed = true
        list[53].isBlue = true

        list[0].owner = Character.GREEN
        list[8].owner = Character.YELLOW
        list[45].owner = Character.RED
        list[53].owner = Character.BLUE
        return list
    }
}