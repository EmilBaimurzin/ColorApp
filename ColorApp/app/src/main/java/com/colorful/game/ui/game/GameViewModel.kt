package com.colorful.game.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorful.game.core.library.random
import com.colorful.game.domain.game.Character
import com.colorful.game.domain.game.GameRepository
import com.colorful.game.domain.game.ItemGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val repository = GameRepository()
    var gameState = false
    var playerColor = Character.BLUE

    private val _list = MutableLiveData(repository.initList())
    val list: LiveData<List<ItemGame>> = _list

    private val _redScores = MutableLiveData(0)
    val redScores: LiveData<Int> = _redScores

    private val _greenScores = MutableLiveData(0)
    val greenScores: LiveData<Int> = _greenScores

    private val _blueScores = MutableLiveData(0)
    val blueScores: LiveData<Int> = _blueScores

    private val _yellowScores = MutableLiveData(0)
    val yellowScores: LiveData<Int> = _yellowScores

    private val _timer = MutableLiveData(50)
    val timer: LiveData<Int> = _timer

    var canBlueMove = true
    var canRedMove = true
    var canGreenMove = true
    var canYellowMove = true

    private var upScope: CoroutineScope = CoroutineScope(Dispatchers.Default).apply {
        cancel()
    }
    private var downScope: CoroutineScope = CoroutineScope(Dispatchers.Default).apply {
        cancel()
    }
    private var leftScope: CoroutineScope = CoroutineScope(Dispatchers.Default).apply {
        cancel()
    }
    private var rightScope: CoroutineScope = CoroutineScope(Dispatchers.Default).apply {
        cancel()
    }
    private var botsMovingScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    fun stopPlayer() {
        upScope.cancel()
        downScope.cancel()
        leftScope.cancel()
        rightScope.cancel()
    }

    fun findMaxScores(): Character {
        val redScores = _redScores.value!!
        val greenScores = _greenScores.value!!
        val blueScores = _blueScores.value!!
        val yellowScores = _yellowScores.value!!
        return when {
            redScores > greenScores && redScores > blueScores && redScores > yellowScores -> Character.RED
            greenScores > redScores && greenScores > blueScores && greenScores > yellowScores -> Character.GREEN
            blueScores > greenScores && blueScores > redScores && blueScores > yellowScores -> Character.BLUE
            else -> Character.YELLOW
        }
    }

    private fun checkBullet(index: Int) {
        if (_list.value!![index].isRed) {
            viewModelScope.launch {
                canRedMove = false
                val newList = _list.value!!
                newList[index].hasBulletLeft = false
                newList[index].hasBulletRight = false
                _list.postValue(newList)
                delay(5000)
                canRedMove = true
            }
        }
        if (_list.value!![index].isBlue) {
            viewModelScope.launch {
                canBlueMove = false
                val newList = _list.value!!
                newList[index].hasBulletLeft = false
                newList[index].hasBulletRight = false
                _list.postValue(newList)
                delay(5000)
                canBlueMove = true
            }
        }
        if (_list.value!![index].isGreen) {
            viewModelScope.launch {
                canGreenMove = false
                val newList = _list.value!!
                newList[index].hasBulletLeft = false
                newList[index].hasBulletRight = false
                _list.postValue(newList)
                delay(5000)
                canGreenMove = true
            }
        }
        if (_list.value!![index].isYellow) {
            viewModelScope.launch {
                canYellowMove = false
                val newList = _list.value!!
                newList[index].hasBulletLeft = false
                newList[index].hasBulletRight = false
                _list.postValue(newList)
                delay(5000)
                canYellowMove = true
            }
        }
    }

    private fun generateBullets() {
        botsMovingScope.launch {
            while (true) {
                delay(5000)
                val randomColumn = 1 random 6
                when (randomColumn) {
                    1 -> {

                    }

                    2 -> {
                        if (1 random 2 == 1) {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[it + 9].hasBulletLeft = true
                                checkBullet(it + 9)
                                delay(250)
                                newList[it + 9].hasBulletLeft = false
                            }
                        } else {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[17 - it].hasBulletRight = true
                                checkBullet(17 - it)
                                delay(250)
                                newList[17 - it].hasBulletRight = false
                            }
                        }
                    }

                    3 -> {
                        if (1 random 2 == 1) {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[it + 18].hasBulletLeft = true
                                checkBullet(it + 18)
                                delay(250)
                                newList[it + 18].hasBulletLeft = false
                            }
                        } else {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[26 - it].hasBulletRight = true
                                checkBullet(26 - it)
                                delay(250)
                                newList[26 - it].hasBulletRight = false
                            }
                        }
                    }

                    4 -> {
                        if (1 random 2 == 1) {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[it + 27].hasBulletLeft = true
                                checkBullet(it + 27)
                                delay(250)
                                newList[it + 27].hasBulletLeft = false
                            }
                        } else {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[35 - it].hasBulletRight = true
                                checkBullet(35 - it)
                                delay(250)
                                newList[35 - it].hasBulletRight = false
                            }
                        }
                    }

                    5 -> {
                        if (1 random 2 == 1) {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[it + 36].hasBulletLeft = true
                                checkBullet(it + 36)
                                delay(250)
                                newList[it + 36].hasBulletLeft = false
                            }
                        } else {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[44 - it].hasBulletRight = true
                                checkBullet(44 - it)
                                delay(250)
                                newList[44 - it].hasBulletRight = false
                            }
                        }
                    }

                    else -> {
                        if (1 random 2 == 1) {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[it + 45].hasBulletLeft = true
                                checkBullet(it + 45)
                                delay(250)
                                newList[it + 45].hasBulletLeft = false
                            }
                        } else {
                            repeat(9) {
                                val newList = _list.value!!.toMutableList()
                                newList[53 - it].hasBulletRight = true
                                checkBullet(53 - it)
                                delay(250)
                                newList[53 - it].hasBulletRight = false
                            }
                        }
                    }
                }
            }
        }
    }

    fun startGame() {
        gameState = true
        botsMovingScope = CoroutineScope(Dispatchers.Default)
        startTimer()
        startBots()
        generateBullets()
    }

    fun stopGame() {
        botsMovingScope.cancel()
    }

    fun endTimer() {
        gameState = false
        _timer.postValue(-1)
    }

    private fun startTimer() {
        botsMovingScope.launch {
            var value = _timer.value!!
            while (value != 0) {
                _timer.postValue(value - 1)
                value -= 1
                delay(1000)
            }
        }
    }

    private fun startBots() {
        when (playerColor) {
            Character.GREEN -> {
                generateYellowMovement()
                generateRedMovement()
                generateBlueMovement()
            }

            Character.YELLOW -> {
                generateGreenMovement()
                generateRedMovement()
                generateBlueMovement()
            }

            Character.RED -> {
                generateYellowMovement()
                generateGreenMovement()
                generateBlueMovement()
            }

            Character.BLUE -> {
                generateYellowMovement()
                generateRedMovement()
                generateGreenMovement()
            }
        }
    }

    fun movePlayerUp() {
        if (!upScope.isActive) {
            rightScope.cancel()
            leftScope.cancel()
            downScope.cancel()
            upScope = CoroutineScope(Dispatchers.Default)
            upScope.launch {
                while (true) {
                    moveCharacterUp(playerColor)
                    delay(500)
                }
            }
        }
    }

    fun movePlayerRight() {
        if (!rightScope.isActive) {
            upScope.cancel()
            leftScope.cancel()
            downScope.cancel()
            rightScope = CoroutineScope(Dispatchers.Default)
            rightScope.launch {
                while (true) {
                    moveCharacterRight(playerColor)
                    delay(500)
                }
            }
        }
    }

    fun movePlayerLeft() {
        if (!leftScope.isActive) {
            rightScope.cancel()
            upScope.cancel()
            downScope.cancel()
            leftScope = CoroutineScope(Dispatchers.Default)
            leftScope.launch {
                while (true) {
                    moveCharacterLeft(playerColor)
                    delay(500)
                }
            }
        }
    }

    fun movePlayerDown() {
        if (!downScope.isActive) {
            rightScope.cancel()
            leftScope.cancel()
            upScope.cancel()
            downScope = CoroutineScope(Dispatchers.Default)
            downScope.launch {
                while (true) {
                    moveCharacterDown(playerColor)
                    delay(500)
                }
            }
        }
    }

    fun moveCharacterUp(character: Character) {
        when (character) {
            Character.GREEN -> {
                if (canGreenMove) {
                    calculateUpIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isGreen }),
                        Character.GREEN
                    )
                }
            }

            Character.YELLOW -> {
                if (canYellowMove) {
                    calculateUpIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isYellow }),
                        Character.YELLOW
                    )
                }
            }

            Character.RED -> {
                if (canRedMove) {
                    calculateUpIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isRed }),
                        Character.RED
                    )
                }
            }

            Character.BLUE -> {
                if (canBlueMove) {
                    calculateUpIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isBlue }),
                        Character.BLUE
                    )
                }
            }
        }
    }

    fun moveCharacterDown(character: Character) {
        when (character) {
            Character.GREEN -> {
                if (canGreenMove) {
                    calculateDownIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isGreen }),
                        Character.GREEN
                    )
                }
            }

            Character.YELLOW -> {
                if (canYellowMove) {
                    calculateDownIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isYellow }),
                        Character.YELLOW
                    )
                }
            }

            Character.RED -> {
                if (canRedMove) {
                    calculateDownIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isRed }),
                        Character.RED
                    )
                }
            }

            Character.BLUE -> {
                if (canBlueMove) {
                    calculateDownIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isBlue }),
                        Character.BLUE
                    )
                }
            }
        }
    }

    fun moveCharacterLeft(character: Character) {
        when (character) {
            Character.GREEN -> {
                if (canGreenMove) {
                    calculateLeftIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isGreen }),
                        Character.GREEN
                    )
                }
            }

            Character.YELLOW -> {
                if (canYellowMove) {
                    calculateLeftIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isYellow }),
                        Character.YELLOW
                    )
                }
            }

            Character.RED -> {
                if (canRedMove) {
                    calculateLeftIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isRed }),
                        Character.RED
                    )
                }
            }

            Character.BLUE -> {
                if (canBlueMove) {
                    calculateLeftIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isBlue }),
                        Character.BLUE
                    )
                }
            }
        }
    }

    fun moveCharacterRight(character: Character) {
        when (character) {
            Character.GREEN -> {
                if (canGreenMove) {
                    calculateRightIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isGreen }),
                        Character.GREEN
                    )
                }
            }

            Character.YELLOW -> {
                if (canYellowMove) {
                    calculateRightIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isYellow }),
                        Character.YELLOW
                    )
                }
            }

            Character.RED -> {
                if (canRedMove) {
                    calculateRightIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isRed }),
                        Character.RED
                    )
                }
            }

            Character.BLUE -> {
                if (canBlueMove) {
                    calculateRightIndex(
                        _list.value!!.indexOf(_list.value!!.find { it.isBlue }),
                        Character.BLUE
                    )
                }
            }
        }
    }

    private fun calculateUpIndex(value: Int, character: Character) {
        if (value - 9 >= 0) {
            val newList = _list.value!!
            if (newList[value - 9].owner != null && newList[value - 9].owner != character) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(25)
                    Character.YELLOW -> increaseYellowScores(25)
                    Character.RED -> increaseRedScores(25)
                    Character.BLUE -> increaseBlueScores(25)
                }
            }
            if (newList[value - 9].owner == null) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(10)
                    Character.YELLOW -> increaseYellowScores(10)
                    Character.RED -> increaseRedScores(10)
                    Character.BLUE -> increaseBlueScores(10)
                }
            }
            newList[value - 9].owner = character
            when (character) {
                Character.GREEN -> {
                    newList[value - 9].isGreen = true
                    newList[value].isGreen = false
                }

                Character.YELLOW -> {
                    newList[value - 9].isYellow = true
                    newList[value].isYellow = false
                }

                Character.RED -> {
                    newList[value - 9].isRed = true
                    newList[value].isRed = false
                }

                Character.BLUE -> {
                    newList[value - 9].isBlue = true
                    newList[value].isBlue = false
                }
            }
            _list.postValue(newList)
        }
    }

    private fun calculateDownIndex(value: Int, character: Character) {
        if (value + 9 <= 53) {
            val newList = _list.value!!
            if (newList[value + 9].owner != null && newList[value + 9].owner != character) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(25)
                    Character.YELLOW -> increaseYellowScores(25)
                    Character.RED -> increaseRedScores(25)
                    Character.BLUE -> increaseBlueScores(25)
                }
            }
            if (newList[value + 9].owner == null) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(10)
                    Character.YELLOW -> increaseYellowScores(10)
                    Character.RED -> increaseRedScores(10)
                    Character.BLUE -> increaseBlueScores(10)
                }
            }
            newList[value + 9].owner = character
            when (character) {
                Character.GREEN -> {
                    newList[value + 9].isGreen = true
                    newList[value].isGreen = false
                }

                Character.YELLOW -> {
                    newList[value + 9].isYellow = true
                    newList[value].isYellow = false
                }

                Character.RED -> {
                    newList[value + 9].isRed = true
                    newList[value].isRed = false
                }

                Character.BLUE -> {
                    newList[value + 9].isBlue = true
                    newList[value].isBlue = false
                }
            }
            _list.postValue(newList)
        }
    }

    private fun calculateLeftIndex(value: Int, character: Character) {
        if (value - 1 >= 0 && value - 1 != 8 && value - 1 != 17 && value - 1 != 26 && value - 1 != 35 && value - 1 != 44) {
            val newList = _list.value!!
            if (newList[value - 1].owner != null && newList[value - 1].owner != character) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(25)
                    Character.YELLOW -> increaseYellowScores(25)
                    Character.RED -> increaseRedScores(25)
                    Character.BLUE -> increaseBlueScores(25)
                }
            }
            if (newList[value - 1].owner == null) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(10)
                    Character.YELLOW -> increaseYellowScores(10)
                    Character.RED -> increaseRedScores(10)
                    Character.BLUE -> increaseBlueScores(10)
                }
            }
            newList[value - 1].owner = character
            when (character) {
                Character.GREEN -> {
                    newList[value - 1].isGreen = true
                    newList[value].isGreen = false
                }

                Character.YELLOW -> {
                    newList[value - 1].isYellow = true
                    newList[value].isYellow = false
                }

                Character.RED -> {
                    newList[value - 1].isRed = true
                    newList[value].isRed = false
                }

                Character.BLUE -> {
                    newList[value - 1].isBlue = true
                    newList[value].isBlue = false
                }
            }
            _list.postValue(newList)
        }
    }

    private fun calculateRightIndex(value: Int, character: Character) {
        if (value + 1 <= 53 && value + 1 != 9 && value + 1 != 18 && value + 1 != 27 && value + 1 != 36 && value + 1 != 43) {
            val newList = _list.value!!
            if (newList[value + 1].owner != null && newList[value + 1].owner != character) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(25)
                    Character.YELLOW -> increaseYellowScores(25)
                    Character.RED -> increaseRedScores(25)
                    Character.BLUE -> increaseBlueScores(25)
                }
            }
            if (newList[value + 1].owner == null) {
                when (character) {
                    Character.GREEN -> increaseGreenScores(10)
                    Character.YELLOW -> increaseYellowScores(10)
                    Character.RED -> increaseRedScores(10)
                    Character.BLUE -> increaseBlueScores(10)
                }
            }
            newList[value + 1].owner = character
            when (character) {
                Character.GREEN -> {
                    newList[value + 1].isGreen = true
                    newList[value].isGreen = false
                }

                Character.YELLOW -> {
                    newList[value + 1].isYellow = true
                    newList[value].isYellow = false
                }

                Character.RED -> {
                    newList[value + 1].isRed = true
                    newList[value].isRed = false
                }

                Character.BLUE -> {
                    newList[value + 1].isBlue = true
                    newList[value].isBlue = false
                }
            }
            _list.postValue(newList)
        }
    }

    fun generateGreenMovement() {
        botsMovingScope.launch {
            while (true) {
                when (1 random 4) {
                    1 -> moveCharacterUp(Character.GREEN)
                    2 -> moveCharacterLeft(Character.GREEN)
                    3 -> moveCharacterRight(Character.GREEN)
                    else -> moveCharacterDown(Character.GREEN)
                }
                delay(500)
            }
        }
    }

    fun generateRedMovement() {
        botsMovingScope.launch {
            while (true) {
                when (1 random 4) {
                    1 -> moveCharacterUp(Character.RED)
                    2 -> moveCharacterLeft(Character.RED)
                    3 -> moveCharacterRight(Character.RED)
                    else -> moveCharacterDown(Character.RED)
                }
                delay(500)
            }
        }
    }

    fun generateBlueMovement() {
        botsMovingScope.launch {
            while (true) {
                when (1 random 4) {
                    1 -> moveCharacterUp(Character.BLUE)
                    2 -> moveCharacterLeft(Character.BLUE)
                    3 -> moveCharacterRight(Character.BLUE)
                    else -> moveCharacterDown(Character.BLUE)
                }
                delay(500)
            }
        }
    }

    fun generateYellowMovement() {
        botsMovingScope.launch {
            while (true) {
                when (1 random 4) {
                    1 -> moveCharacterUp(Character.YELLOW)
                    2 -> moveCharacterLeft(Character.YELLOW)
                    3 -> moveCharacterRight(Character.YELLOW)
                    else -> moveCharacterDown(Character.YELLOW)
                }
                delay(500)
            }
        }
    }


    fun increaseRedScores(value: Int) = _redScores.postValue(_redScores.value!! + value)
    fun increaseGreenScores(value: Int) = _greenScores.postValue(_greenScores.value!! + value)
    fun increaseBlueScores(value: Int) = _blueScores.postValue(_blueScores.value!! + value)
    fun increaseYellowScores(value: Int) = _yellowScores.postValue(_yellowScores.value!! + value)

}