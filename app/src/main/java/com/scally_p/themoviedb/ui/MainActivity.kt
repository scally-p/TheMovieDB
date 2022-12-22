package com.scally_p.themoviedb.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.scally_p.themoviedb.data.model.Result
import com.scally_p.themoviedb.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    MovieAdapter.OnAdapterViewClick {

    private val tag: String = MainActivity::class.java.name

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        prepareViews()
        prepareData()
    }

    override fun onResume() {
        super.onResume()
        if (movieAdapter.itemCount > 0) binding.shimmerFrameLayout.isVisible = true
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
    }

    override fun onRefresh() {
        viewModel.getUpcomingMovies(1)
    }

    override fun onMovieItemClick(result: Result) {
//        binding.one.poster.transitionName = "poster_transition"
//
//        binding.one.content.setOnClickListener {
//        val intent = Intent(this, DetailsActivity::class.java)
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            binding.one.poster,
//            ViewCompat.getTransitionName(binding.one.poster)!!
//        )
//        startActivity(intent, options.toBundle())
//        }
    }

    private fun prepareViews() {
        binding.swipeRefreshLayout.setOnRefreshListener(this)

        movieAdapter = MovieAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    private fun prepareData() {
        viewModel.observeMoviesLiveData().observe(this) { movieList ->
            movieAdapter.setMovieList(movieList)
        }

        viewModel.observeErrorMessage().observe(this) { message ->
            Log.d(tag, message)
        }

        viewModel.observeLoading().observe(this) { loading ->
            if (loading) {
                if (movieAdapter.itemCount > 0) binding.shimmerFrameLayout.isVisible = true
                binding.shimmerFrameLayout.startShimmer()
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.setUpcomingMovies()
        viewModel.getUpcomingMovies(1)
    }
}