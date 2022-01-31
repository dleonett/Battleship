package com.leonett.battleship.data

import com.leonett.battleship.logic.Game

class GameRepository {

    fun fetchGame(): Game {
        return Game()
    }

    fun clearGame() {

    }

}