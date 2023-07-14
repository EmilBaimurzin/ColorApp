package com.colorful.game.ui.game

import androidx.lifecycle.ViewModel
import com.colorful.game.domain.game.Character

class CallbackViewModel: ViewModel() {
    var callback: ((character: Character) -> Unit)? = null
    var callbackPause: (() -> Unit)? = null
}