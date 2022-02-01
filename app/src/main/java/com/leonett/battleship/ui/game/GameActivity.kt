package com.leonett.battleship.ui.game

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.leonett.battleship.ui.compose.game.GameScreenContainer
import com.leonett.battleship.ui.compose.theme.BattleshipTheme

class GameActivity : AppCompatActivity() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BattleshipTheme {
                GameScreenContainer(
                    gameViewModel,
                    gameViewModel::fire,
                    gameViewModel::next,
                    gameViewModel::clearGame
                )
            }
        }
    }

}
