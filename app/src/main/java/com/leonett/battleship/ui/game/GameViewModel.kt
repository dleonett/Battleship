package com.leonett.battleship.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonett.battleship.logic.Game
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private var game = Game()

    private val _state = MutableStateFlow<GameScreenState>(GameScreenState.Idle)
    val state: StateFlow<GameScreenState>
        get() = _state

    init {
        _state.value = GameScreenState.Default(game)
    }

    fun fire(x: Int, y: Int) {
        if (game.fire(x, y)) {
            _state.value = GameScreenState.Default(game)
        } else {
            _state.value = GameScreenState.Default(game)

            viewModelScope.launch {
                delay(500)

                game.switch()
                _state.value = GameScreenState.Middle(game)
            }
        }
    }

    fun next() {
        _state.value = GameScreenState.Default(game)
    }

    fun clearGame() {
        game.clear()
        _state.value = GameScreenState.Default(game)
    }

}
