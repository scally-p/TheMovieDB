package com.scally_p.themoviedb.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.scally_p.themoviedb.ui.details.DetailsActivity
import com.scally_p.themoviedb.ui.main.MainActivity

class LauncherActivity : AppCompatActivity() {

    private val tag: String = DetailsActivity::class.java.name

    private lateinit var viewModel: GenresViewModel
    private var loading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[GenresViewModel::class.java]
        loading = viewModel.getGenres().isEmpty()

        splashScreen.apply {
            setKeepOnScreenCondition {
                Log.d(tag, "splashScreen - loading: $loading")
                loading
            }

            setOnExitAnimationListener {
                startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
                finish()
            }
        }

        prepareData()
    }

    private fun prepareData() {
        viewModel.observeLoading().observe(this) { loading ->
            this.loading = loading
        }

        viewModel.fetchGenres()
    }
}