package com.example.practice_exploraholic
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputBinding
import com.example.practice_exploraholic.databinding.ActivityMainBinding

public const val SPLASH_TIME = 4000L

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    fun isValidString(str: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.myLooper()!!).postDelayed(
            {
                val intent= Intent(this,LoginScreen::class.java)
                startActivity(intent)
                finish()
            },
            SPLASH_TIME
        )

        val emails = arrayOf<String>("hello@gmail.com", "one.com")

        emails.forEach {
            Log.d("MainActivity","is valid email $it => ${isValidString(it)}")
        }

        val fadeInAnim = AnimationUtils.loadAnimation(this,R.anim.anim)
        binding.applogo.animation=fadeInAnim
    }
}