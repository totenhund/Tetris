package totenhund.com.tetris

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import totenhund.com.tetris.models.AppModel
import totenhund.com.tetris.storage.AppPreferences
import totenhund.com.tetris.view.TetrisView

class GameActivity : AppCompatActivity() {

    var highScore: TextView? = null
    var currentScore: TextView? = null
    private lateinit var tetrisView: TetrisView

    var appPreferences: AppPreferences? = null
    private val appModel: AppModel = AppModel()

    @SuppressLint("ClickableViewAccessibility")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        appPreferences = AppPreferences(this)
        appModel.setPreferences(appPreferences)

        val btnRestart = findViewById<Button>(R.id.restartButton)
        highScore = findViewById<TextView>(R.id.high_score)
        currentScore = findViewById<TextView>(R.id.currentScore)
        tetrisView = findViewById<TetrisView>(R.id.view_tetris)
        tetrisView.setActivity(this)
        tetrisView.setModel(appModel)

        tetrisView.setOnTouchListener(this::onTetrisViewTouch)
        btnRestart.setOnClickListener(this::btnRestartClick)

        updateHighScore()
        updateCurrentScore()
    }

    private fun btnRestartClick(view: View) {
        appModel.restartGame()
    }

    private fun onTetrisViewTouch(view: View, event: MotionEvent): Boolean {
        if (appModel.isGameOver() || appModel.isGameAwaitingStart()) {
            appModel.startGame()
            tetrisView.setGameCommandWithDelay(AppModel.Motions.DOWN)

        } else if(appModel.isGameActive()) {
            when (resolveTouchDirection(view, event)) {
                0 -> moveTetromino(AppModel.Motions.LEFT)
                1 -> moveTetromino(AppModel.Motions.ROTATE)
                2 -> moveTetromino(AppModel.Motions.DOWN)
                3 -> moveTetromino(AppModel.Motions.RIGHT)
            }
        }
        return true
    }

    private fun resolveTouchDirection(view: View, event: MotionEvent):
            Int {
        val x = event.x / view.width
        val y = event.y / view.height

        var blockX = (view as TetrisView).currentX
        var blockY = (view as TetrisView).currentY
        val direction: Int
        direction = if (y > x) {
            if (x > 1 - y) 2 else 0
        }
        else {
            if (x > 1 - y) 3 else 1
        }

        return direction
    }

    private fun moveTetromino(motion: AppModel.Motions) {
        if (appModel.isGameActive()) {
            tetrisView.setGameCommand(motion)
        }
    }

    private fun updateHighScore() {
        highScore?.text = "${appPreferences?.getHighScore()}"
    }

    private fun updateCurrentScore() {
        currentScore?.text = "0"
    }

}