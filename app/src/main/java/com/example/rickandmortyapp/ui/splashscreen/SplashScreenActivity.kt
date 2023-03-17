package com.example.rickandmortyapp.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.ActivitySplashScreenBinding
import com.example.rickandmortyapp.ui.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var firstOpen : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        firstOpen = this.getSharedPreferences("FirstOpen", Context.MODE_PRIVATE)
        if(!firstOpen.getBoolean("firstopen",false)){
            binding.welcome.visibility = View.GONE
            binding.hello.visibility = View.VISIBLE
        }else{
            binding.welcome.visibility = View.VISIBLE
            binding.hello.visibility = View.GONE
            firstOpen.edit().putBoolean("firstopen",false).apply()
        }

        binding.apply {
            imageView.alpha = 0f
            imageView.animate().setDuration(1500).alpha(1f).withEndAction{
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }

    }
}