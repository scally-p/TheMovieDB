package com.scally_p.themoviedb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import com.scally_p.themoviedb.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.one.poster.transitionName = "poster_transition"

        binding.one.content.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                binding.one.poster,
                ViewCompat.getTransitionName(binding.one.poster)!!
            )
            startActivity(intent, options.toBundle())
        }
    }
}