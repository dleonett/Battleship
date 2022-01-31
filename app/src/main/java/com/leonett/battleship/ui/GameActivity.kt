package com.leonett.battleship.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.leonett.battleship.ui.compose.game.GameScreenContainer
import com.leonett.battleship.ui.compose.theme.BattleshipTheme
import com.leonett.battleship.ui.game.GameScreenState

class GameActivity : AppCompatActivity() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameViewModel.state.observe(this, Observer {
            when (it) {
                is GameScreenState.Idle -> {
                    Log.d(TAG, "observe: Idle")
                }
                is GameScreenState.Default -> {
                    Log.d(TAG, "observe: Default ${it.game}")
                    Log.d(TAG, "observe: Default ${it.game.board.tiles[0][0]}")
                }
            }
        })

        setContent {
            BattleshipTheme {
                GameScreenContainer(gameViewModel, gameViewModel::fire)
            }
        }
    }

    companion object {
        val TAG: String = GameActivity::class.java.simpleName
    }
}