package com.leonett.battleship.ui.game

import com.leonett.battleship.logic.Game

sealed class GameScreenState {
    object Idle : GameScreenState()
    data class Default(val game: Game) : GameScreenState()
    data class Middle(val game: Game) : GameScreenState()
    data class Finished(val game: Game) : GameScreenState()
}