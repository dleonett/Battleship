package com.leonett.battleship.logic

import kotlin.random.Random

data class Board(var size: Int = 10, var ships: List<Ship>, var player: Player) {

    var tiles: Array<Array<Tile>> = Array(size) { Array(size) { Tile() } }

    fun fire(pointX: Int, pointY: Int): Boolean {
        if (pointX !in 0 until size || pointY !in 0 until size) {
            return false
        }

        tiles[pointX][pointY].discovered = true

        return tiles[pointX][pointY].ship !is Ship.None
    }

    fun shuffle() {
        clear()

        ships.forEach { attemptToPlaceShip(it) }
    }

    private fun attemptToPlaceShip(ship: Ship) {
        if (ship !is Ship.None) {
            do {
                val isVertical = Random.nextBoolean()
                val pointX = (0 until size).random()
                val pointY = (0 until size).random()

                val tiles = generateShipTiles(ship.size, isVertical, pointX, pointY)
                if (tiles.isNotEmpty()) {
                    placeShip(ship, tiles)
                }
            } while (tiles.isEmpty())
        }
    }

    private fun generateShipTiles(
        shipSize: Int,
        isVertical: Boolean,
        startPointX: Int,
        startPointY: Int
    ): List<Pair<Int, Int>> {
        if (tiles[startPointX][startPointY].ship !is Ship.None) {
            return emptyList()
        }

        val resultList = arrayListOf<Pair<Int, Int>>()
        var remainingTiles = shipSize
        var pointX = startPointX
        var pointY = startPointY

        while (remainingTiles > 0) {
            pointX = if (isVertical) pointX else ++pointX
            pointY = if (isVertical) ++pointY else pointY

            if (pointX !in 0 until size || pointY !in 0 until size) {
                return emptyList()
            }

            if (tiles[pointX][pointY].ship !is Ship.None) {
                return emptyList()
            }

            resultList.add(Pair(pointX, pointY))

            remainingTiles--
        }

        return resultList
    }

    private fun placeShip(ship: Ship, tiles: List<Pair<Int, Int>>) {
        tiles.forEach {
            this.tiles[it.first][it.second].ship = ship
        }

        ship.tiles = tiles
    }

    fun clear() {
        for (x in 0 until size) {
            for (y in 0 until size) {
                tiles[x][y].ship = Ship.None
            }
        }
    }

}
