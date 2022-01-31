package com.leonett.battleship.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leonett.battleship.logic.Game
import com.leonett.battleship.ui.game.GameScreenState

class GameViewModel : ViewModel() {

    private var game = Game()

    /*private val _state = MutableStateFlow<GameScreenState>(GameScreenState.Idle)
    val state: StateFlow<GameScreenState>
        get() = _state*/
    private val _state = MutableLiveData<GameScreenState>(GameScreenState.Idle)
    val state: LiveData<GameScreenState>
        get() = _state

    init {
        game.shuffle()
        _state.value = GameScreenState.Default(game)
    }

    fun fire(x: Int, y: Int) {
        game.fire(x, y)
        _state.value = GameScreenState.Default(game)
    }

    fun clearGame() {
        game.clear()
        _state.value = GameScreenState.Default(game)
    }

    companion object {
        val TAG: String = GameViewModel::class.java.simpleName
    }

}