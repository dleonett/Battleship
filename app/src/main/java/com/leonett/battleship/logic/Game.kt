package com.leonett.battleship.logic

import com.leonett.battleship.common.generateRandomString

data class Game(
    var size: Int = 10,
    var board: Board = Board(size),
    var playerOne: Player = Player(),
    var playerTwo: Player = Player()
) {

    fun shuffle() {
        board.shuffle()
        playerOne.name = generateRandomString(10)
        playerTwo.name = generateRandomString(10)
    }

    fun fire(x: Int, y: Int) {
        board.fire(x, y)
    }

    fun clear() {
        board.clear()
    }

}
