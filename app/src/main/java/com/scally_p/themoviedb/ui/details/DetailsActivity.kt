package com.scally_p.themoviedb.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.scally_p.themoviedb.R
import com.scally_p.themoviedb.databinding.ActivityDetailsBinding
import com.scally_p.themoviedb.extension.get5StarRating
import com.scally_p.themoviedb.extension.toDuration
import com.scally_p.themoviedb.util.Constants
import com.scally_p.themoviedb.util.ImageUtils
import com.scally_p.themoviedb.util.Utils
import kotlin.math.abs


class DetailsActivity : AppCompatActivity(), OnClickListener, OnRefreshListener {

    private val tag: String = DetailsActivity::class.java.name

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportPostponeEnterTransition()

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        prepareViews()
        prepareData()
    }

    override fun onResume() {
        super.onResume()
//        if (movieAdapter.itemCount > 0) binding.shimmerFrameLayout.isVisible = true
//        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
//        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
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

    override fun onRefresh() {
        viewModel.getDetails()
    }

    private fun prepareViews() {
        binding.back.setOnClickListener(this)
        binding.homepage.setOnClickListener(this)

        binding.swipeRefreshLayout.setOnRefreshListener(this)

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

    private fun prepareData() {
        viewModel.observeDetailsLiveData().observe(this) { details ->
            binding.poster2.transitionName = details?.id?.toString()

            binding.title.text = details.title
            binding.ratingBar.rating = details.vote_average.get5StarRating().toFloat()
            Log.d(tag, "get5StarRating - ${details.vote_average.get5StarRating()}")

            binding.rating.text = binding.root.resources.getString(
                R.string.rating,
                details.vote_average.toString(),
                details.vote_count.toString()
            )
            binding.genre.text = "Action | Comedy | Family"
            binding.releaseDate.text = binding.root.resources.getString(
                R.string.released_on,
                Utils.formatDate(details.release_date.toString())
            )
            binding.duration.text = details.runtime.toDuration()
            binding.overview.text = details.overview
            binding.homepage.text = details.homepage
            ImageUtils.setGlideImage(binding.root, binding.backdrop, Constants.Urls.IMAGE + "w500" + details.poster_path)

            Glide.with(binding.root)
                .load(Constants.Urls.IMAGE + "w500" + details.poster_path)
                .listener(
                    object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: com.bumptech.glide.request.target.Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            supportStartPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: com.bumptech.glide.request.target.Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            supportStartPostponedEnterTransition()
                            return false
                        }
                    })
                .into(binding.poster2)
        }

        viewModel.observeErrorMessage().observe(this) { message ->
            Log.d(tag, message)
        }

        viewModel.observeLoading().observe(this) { loading ->
            if (loading) {
//                if (movieAdapter.itemCount > 0) binding.shimmerFrameLayout.isVisible = true
//                binding.shimmerFrameLayout.startShimmer()
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
//                binding.shimmerFrameLayout.stopShimmer()
//                binding.shimmerFrameLayout.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.setId(intent.getIntExtra(Constants.General.ID, 0))
        viewModel.setDetails()
        viewModel.getDetails()
    }
}