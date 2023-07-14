package com.colorful.game.domain.game

data class ItemGame(
    var isRed: Boolean,
    var isGreen: Boolean,
    var isBlue: Boolean,
    var isYellow: Boolean,
    var owner: Character? = null,
    var hasBulletLeft: Boolean = false,
    var hasBulletRight: Boolean = false
)