package com.scally_p.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.scally_p.themoviedb.databinding.ActivityDetailsBinding
import kotlin.math.abs

class DetailsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener(this)
        binding.homepage.setOnClickListener(this)

        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                // Collapsed
                binding.logo.isVisible = true
                binding.poster1.isVisible = false
            } else if (verticalOffset == 0) {
                // Expanded
                binding.logo.isVisible = false
                binding.poster1.isVisible = true
            } else {
                // Somewhere in between
                binding.logo.isVisible = false
                binding.poster1.isVisible = true
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.back -> {
                finish()
            }
            R.id.homepage -> {

            }
        }
    }
}