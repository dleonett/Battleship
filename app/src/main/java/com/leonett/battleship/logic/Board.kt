package com.leonett.battleship.logic

data class Board(var size: Int = 10) {

    var tiles: Array<Array<Tile>> = Array(size) { Array(size) { Tile() } }

    fun fire(x: Int, y: Int) {
        if (x !in 0..size || y !in 0..size) return

        tiles[x][y].fire()
    }

    fun shuffle() {
        clear()

        tiles[(0..9).random()][(0..9).random()].ship = Ship.Ship1
        tiles[(0..9).random()][(0..9).random()].ship = Ship.Ship2
        tiles[(0..9).random()][(0..9).random()].ship = Ship.Ship3
        tiles[(0..9).random()][(0..9).random()].ship = Ship.Ship4
        tiles[(0..9).random()][(0..9).random()].ship = Ship.Ship5
    }

    fun clear() {
        for (x in 0 until size) {
            for (y in 0 until size) {
                tiles[x][y].ship = Ship.None
            }
        }
    }

}

fun Board.duplicate(): Board {
    val board = Board()
    board.size = this.size
    board.tiles = this.tiles
    return board
}