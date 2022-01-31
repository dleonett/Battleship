package com.leonett.battleship.logic

class Game {

    var boardPlayerOne: Board
    var boardPlayerTwo: Board
    var activeBoard: Board
    var playerOne: Player
    var playerTwo: Player
    var activePlayer: Player

    init {
        // Players
        playerOne = Player("Player 1")
        playerTwo = Player("Player 2")
        activePlayer = playerOne

        // Boards
        val shipsPlayerOne = listOf(
            Ship.ShipA(player = playerOne),
            Ship.ShipB(player = playerOne),
            Ship.ShipC(player = playerOne),
            Ship.ShipD(player = playerOne),
            Ship.ShipE(player = playerOne)
        )
        val shipsPlayerTwo = listOf(
            Ship.ShipA(player = playerTwo),
            Ship.ShipB(player = playerTwo),
            Ship.ShipC(player = playerTwo),
            Ship.ShipD(player = playerTwo),
            Ship.ShipE(player = playerTwo)
        )
        boardPlayerOne = Board(BOARD_SIZE, shipsPlayerOne, playerOne)
        boardPlayerTwo = Board(BOARD_SIZE, shipsPlayerTwo, playerTwo)
        activeBoard = boardPlayerTwo

        boardPlayerOne.shuffle()
        boardPlayerTwo.shuffle()
    }

    fun fire(x: Int, y: Int): Boolean {
        return activeBoard.fire(x, y)
    }

    fun switch() {
        switchActivePlayer()
        switchActiveBoard()
    }

    private fun switchActivePlayer() {
        activePlayer = if (activePlayer.name == playerOne.name) playerTwo else playerOne
    }

    private fun switchActiveBoard() {
        activeBoard = if (activeBoard == boardPlayerOne) boardPlayerTwo else boardPlayerOne
    }

    fun clear() {
        activeBoard.clear()
    }

    companion object {
        const val BOARD_SIZE = 10
    }

}
