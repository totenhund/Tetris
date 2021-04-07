package totenhund.com.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import totenhund.com.tetris.storage.AppPreferences
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    var highScore: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNewGame = findViewById<Button>(R.id.newGameButton)
        val btnResetScore = findViewById<Button>(R.id.resetScoreButton)
        val btnExit = findViewById<Button>(R.id.exitButton)

        highScore = findViewById<TextView>(R.id.scoreTextView)

        btnNewGame.setOnClickListener(this::runNewGameClick)
        btnResetScore.setOnClickListener(this::resetScoreClick)
        btnExit.setOnClickListener(this::exitGameClick)
    }

    private fun runNewGameClick(view: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun resetScoreClick(view: View){
        val preferences = AppPreferences(this)
        preferences.clearHighScore()
        Snackbar.make(view, "Score successfully reset", Snackbar.LENGTH_SHORT).show()
        highScore?.text = "High score: ${preferences.getHighScore()}"
    }

    private fun exitGameClick(view: View){
        exitProcess(0)
    }

}