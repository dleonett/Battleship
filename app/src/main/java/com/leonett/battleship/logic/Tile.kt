package com.leonett.battleship.logic

data class Tile(var ship: Ship = Ship.None, var discovered: Boolean = false) {

    fun fire() {
        if (discovered) return

        discovered = true
    }

}