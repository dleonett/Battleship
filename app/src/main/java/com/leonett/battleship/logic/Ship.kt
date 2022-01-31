package com.leonett.battleship.logic

sealed class Ship(
    val size: Int = 0,
    var discovered: Boolean = false,
    var tiles: List<Pair<Int, Int>> = emptyList(),
    open var player: Player?
) {
    object None : Ship(player = null)
    data class ShipA(override var player: Player?) : Ship(2, player = player)
    data class ShipB(override var player: Player?) : Ship(3, player = player)
    data class ShipC(override var player: Player?) : Ship(3, player = player)
    data class ShipD(override var player: Player?) : Ship(4, player = player)
    data class ShipE(override var player: Player?) : Ship(5, player = player)
}