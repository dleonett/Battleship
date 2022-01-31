package com.leonett.battleship.ui.compose.game

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Map
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leonett.battleship.logic.Game
import com.leonett.battleship.logic.Ship
import com.leonett.battleship.ui.GameViewModel
import com.leonett.battleship.ui.compose.theme.BattleshipTheme
import com.leonett.battleship.ui.game.GameScreenState

val TAG = "GameScreen"

@Composable
fun GameScreen(screenState: GameScreenState, onTileClick: ((x: Int, y: Int) -> Unit)? = null) {
    when (screenState) {
        is GameScreenState.Idle -> {
            // Do nothing
            Log.d(TAG, "Idle")
        }
        is GameScreenState.Default -> {
            Log.d(TAG, "Default")

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (y in (0 until screenState.game.board.size)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (x in (0 until screenState.game.board.size)) {
                            val tile = screenState.game.board.tiles[x][y]
                            val isDiscovered: MutableState<Boolean> =
                                remember { mutableStateOf(tile.discovered) }
                            val ship: MutableState<Ship> = remember { mutableStateOf(tile.ship) }

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
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameScreenContainer(viewModel: GameViewModel, onCellClick: ((x: Int, y: Int) -> Unit)? = null) {
    //val state by remember(viewModel) { viewModel.state }.collectAsState()
    val state by viewModel.state.observeAsState(initial = GameScreenState.Idle)
    GameScreen(state, onCellClick)
}

@Preview
@Composable
fun GameScreenPreview() {
    BattleshipTheme {
        GameScreen(GameScreenState.Default(Game()))
    }
}