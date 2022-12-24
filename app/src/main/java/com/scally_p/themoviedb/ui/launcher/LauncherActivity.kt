package com.scally_p.themoviedb.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.scally_p.themoviedb.ui.details.DetailsActivity
import com.scally_p.themoviedb.ui.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class LauncherActivity : AppCompatActivity(), KoinComponent {

    private val tag: String = DetailsActivity::class.java.name

    private var loading = true

    private val viewModel: GenresViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        loading = viewModel.genres.isEmpty()

        splashScreen.apply {
            setKeepOnScreenCondition {
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
        viewModel.observeErrorMessage().observe(this) { message ->
            Log.d(tag, "<<<<< Error Message>>>>>\n$message")
        }

        viewModel.observeLoading().observe(this) { loading ->
            this.loading = loading
        }

        viewModel.fetchGenres()
    }
}