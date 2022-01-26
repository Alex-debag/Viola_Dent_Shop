package soft.shope.violadent

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        /**  We remove below ActionBar */
        supportActionBar?.hide()

    }

}