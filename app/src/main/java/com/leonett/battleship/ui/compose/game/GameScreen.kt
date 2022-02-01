package com.leonett.battleship.ui.compose.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leonett.battleship.logic.Game
import com.leonett.battleship.logic.Ship
import com.leonett.battleship.ui.compose.theme.BattleshipTheme
import com.leonett.battleship.ui.game.GameScreenState
import com.leonett.battleship.ui.game.GameViewModel

@Composable
fun GameScreen(
    screenState: GameScreenState,
    onTileClick: ((x: Int, y: Int) -> Unit)? = null,
    onNextPlayerClick: (() -> Unit)? = null,
    onGameFinishedClick: (() -> Unit)? = null
) {
    when (screenState) {
        is GameScreenState.Idle -> {
            // Do nothing
        }
        is GameScreenState.Middle -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Next player: ${screenState.game.activePlayer.name}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onNextPlayerClick?.invoke() }) {
                    Text(text = "Next")
                }
            }
        }
        is GameScreenState.Default -> {
            val game = screenState.game
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Active player: ${game.activePlayer.name}")
                Spacer(modifier = Modifier.height(16.dp))
                OpponentBoard(game, onTileClick)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        PlayerBoard(game)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        PlayerShips(game)
                    }
                }
            }
        }
        is GameScreenState.Finished -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Game over")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Winner: ${screenState.game.activePlayer.name}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onGameFinishedClick?.invoke() }) {
                    Text(text = "Play again")
                }
            }
        }
    }
}

@Composable
fun OpponentBoard(game: Game, onTileClick: ((x: Int, y: Int) -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (x in (-1 until game.opponentBoard.size)) {
                if (x == -1) {
                    Spacer(modifier = Modifier.width(20.dp))
                } else {
                    Text(
                        text = "${x + 1}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        for (y in (0 until game.opponentBoard.size)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val letter = 'A'
                Text(
                    text = "${letter + y}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(20.dp)
                )
                for (x in (0 until game.opponentBoard.size)) {
                    val tile = game.opponentBoard.tiles[x][y]
                    val isDiscovered = mutableStateOf(tile.discovered)
                    val ship = mutableStateOf(tile.ship)

                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(if (ship.value.discovered) Color.Gray else Color.Transparent)
                            .border(1.dp, Color.Black),
                        enabled = !isDiscovered.value,
                        onClick = {
                            onTileClick?.invoke(x, y)
                            isDiscovered.value = true
                        }
                    ) {
                        if (isDiscovered.value) {
                            val icon = when (ship.value) {
                                is Ship.None -> Icons.Outlined.Circle
                                else -> Icons.Filled.Circle
                            }
                            val color = when (ship.value) {
                                is Ship.None -> Color.Black
                                else -> Color.Red
                            }

                            Icon(
                                imageVector = icon,
                                tint = color,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerBoard(game: Game) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (x in (-1 until game.playerBoard.size)) {
                if (x == -1) {
                    Spacer(modifier = Modifier.width(12.dp))
                } else {
                    Text(
                        text = "${x + 1}",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        for (y in (0 until game.playerBoard.size)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val letter = 'A'
                Text(
                    text = "${letter + y}",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(12.dp)
                )
                for (x in (0 until game.playerBoard.size)) {
                    val tile = game.playerBoard.tiles[x][y]
                    val isDiscovered = mutableStateOf(tile.discovered)
                    val ship = mutableStateOf(tile.ship)

                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(if (ship.value !is Ship.None) Color.Gray else Color.Transparent)
                            .border(1.dp, Color.Black),
                        enabled = false,
                        onClick = { }
                    ) {
                        if (isDiscovered.value) {
                            val icon = when (ship.value) {
                                is Ship.None -> Icons.Outlined.Circle
                                else -> Icons.Filled.Circle
                            }
                            val color = when (ship.value) {
                                is Ship.None -> Color.Black
                                else -> Color.Red
                            }

                            Icon(
                                imageVector = icon,
                                tint = color,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerShips(game: Game) {
    Column(modifier = Modifier.fillMaxWidth()) {
        game.playerBoard.ships.forEachIndexed { index, item ->
            Row(modifier = Modifier.fillMaxWidth()) {
                item.tiles.forEach {
                    Spacer(
                        modifier = Modifier
                            .size(24.dp)
                            .background(if (game.playerBoard.tiles[it.first][it.second].discovered) Color.Red else Color.Gray)
                            .border(1.dp, Color.Black)
                    )
                }
            }
            if (index < game.playerBoard.ships.size - 1) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun GameScreenContainer(
    viewModel: GameViewModel,
    onCellClick: ((x: Int, y: Int) -> Unit)? = null,
    onNextPlayerClick: (() -> Unit)? = null,
    onGameFinishedClick: (() -> Unit)? = null
) {
    val state by remember(viewModel) { viewModel.state }.collectAsState()
    GameScreen(state, onCellClick, onNextPlayerClick, onGameFinishedClick)
}

@Preview
@Composable
fun GameScreenPreview() {
    BattleshipTheme {
        GameScreen(GameScreenState.Default(Game()))
    }
}