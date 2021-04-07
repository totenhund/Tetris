package totenhund.com.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import totenhund.com.tetris.storage.AppPreferences

class GameActivity : AppCompatActivity() {

    var highScore: TextView? = null
    var currentScore: TextView? = null
    var appPreferences: AppPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val btnRestart = findViewById<Button>(R.id.restartButton)
        highScore = findViewById(R.id.high_score)
        currentScore = findViewById(R.id.currentScore)

    }

    private fun updateHighScore(){
        highScore?.text = "${appPreferences?.getHighScore()}"
    }

    private fun updateCurrentScore(){
        currentScore?.text = "0"
    }

}