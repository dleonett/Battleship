package com.leonett.battleship.logic

import com.leonett.battleship.common.generateRandomString

data class Player(var name: String = generateRandomString()) {
}