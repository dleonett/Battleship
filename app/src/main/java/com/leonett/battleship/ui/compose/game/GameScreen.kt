package com.leonett.battleship.ui.compose.game

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leonett.battleship.logic.Game
import com.leonett.battleship.logic.Ship
import com.leonett.battleship.ui.compose.theme.BattleshipTheme
import com.leonett.battleship.ui.game.GameScreenState
import com.leonett.battleship.ui.game.GameViewModel

@Composable
fun GameScreen(
    screenState: GameScreenState,
    onTileClick: ((x: Int, y: Int) -> Unit)? = null,
    onNextPlayerClick: (() -> Unit)? = null
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Active player: ${screenState.game.activePlayer.name}")
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (y in (0 until screenState.game.activeBoard.size)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (x in (0 until screenState.game.activeBoard.size)) {
                                val tile = screenState.game.activeBoard.tiles[x][y]
                                val isDiscovered = mutableStateOf(tile.discovered)
                                val ship = mutableStateOf(tile.ship)

                                IconButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .border(1.dp, Color.Gray),
                                    enabled = !isDiscovered.value,
                                    onClick = {
                                        onTileClick?.invoke(x, y)
                                        isDiscovered.value = true
                                    }
                                ) {
                                    if (isDiscovered.value) {
                                        val icon = if (ship.value is Ship.None)
                                            Icons.Outlined.Circle
                                        else
                                            Icons.Filled.Circle

                                        Icon(
                                            imageVector = icon,
                                            tint = Color.Red,
                                            contentDescription = null
                                        )
                                    }
                                    /*val icon = when (ship.value) {
                                        Ship.None -> Icons.Outlined.Circle
                                        Ship.ShipA -> Icons.Filled.AcUnit
                                        Ship.ShipB -> Icons.Filled.BabyChangingStation
                                        Ship.ShipC -> Icons.Filled.Cable
                                        Ship.ShipD -> Icons.Filled.Dangerous
                                        Ship.ShipE -> Icons.Filled.Eco
                                    }

                                    Icon(
                                        imageVector = icon,
                                        tint = Color.Red,
                                        contentDescription = null
                                    )*/
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameScreenContainer(
    viewModel: GameViewModel,
    onCellClick: ((x: Int, y: Int) -> Unit)? = null,
    onNextPlayerClick: (() -> Unit)? = null
) {
    val state by remember(viewModel) { viewModel.state }.collectAsState()
    GameScreen(state, onCellClick, onNextPlayerClick)
}

@Preview
@Composable
fun GameScreenPreview() {
    BattleshipTheme {
        GameScreen(GameScreenState.Default(Game()))
    }
}