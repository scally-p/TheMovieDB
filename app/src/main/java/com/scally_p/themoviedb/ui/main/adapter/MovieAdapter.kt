package com.scally_p.themoviedb.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import com.scally_p.themoviedb.*
import com.scally_p.themoviedb.data.local.repository.MoviesRepository
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.databinding.LayoutMovieItemBinding
import com.scally_p.themoviedb.databinding.LayoutMovieItemFooterLoaderBinding
import com.scally_p.themoviedb.extension.get5StarRating
import com.scally_p.themoviedb.util.Constants
import com.scally_p.themoviedb.util.ImageUtils
import com.scally_p.themoviedb.util.Utils

class MovieAdapter(private var onAdapterViewClick: OnAdapterViewClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val tag: String = MovieAdapter::class.java.name

    private var movieList = ArrayList<Result>()
    private val moviesRepository = MoviesRepository()
    private var mFooterLoading: Boolean = false

    fun setMovieList(movieList: List<Result>) {
        this.movieList = movieList as ArrayList<Result>
        notifyItemRangeChanged(0, movieList.size)
        footerLoading = false
    }

    var footerLoading: Boolean
        get() {
            return mFooterLoading
        }
        set(value) {
            mFooterLoading = value
        }


    fun addFooterLoader() {
        movieList.add(Result())
        footerLoading = true
    }

    inner class FooterLoaderItemViewHolder(private val binding: LayoutMovieItemFooterLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var result: Result? = null

        fun bind(result: Result) {
            this.result = result
        }
    }

    inner class ItemViewHolder(private val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var result: Result? = null

        fun bind(result: Result) {
            this.result = result

            binding.title.text = result.title
            binding.genre.text = moviesRepository.getMovieGenresString(result.genre_ids)
            binding.releaseDate.text = binding.root.resources.getString(
                R.string.release_date,
                Utils.formatDate(result.release_date.toString())
            )
            binding.ratingBar.rating = result.vote_average.get5StarRating().toFloat()
            Log.d(tag, "get5StarRating - ${result.vote_average.get5StarRating()}")

            binding.rating.text = binding.root.resources.getString(
                R.string.rating,
                result.vote_average.toString(),
                result.vote_count.toString()
            )
            ImageUtils.setGlideImage(
                binding.root,
                binding.poster,
                Constants.Urls.IMAGE + "w500" + result.poster_path
            )

            binding.content.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            when (p0!!.id) {
                R.id.content -> onAdapterViewClick.onMovieItemClick(result!!, binding.poster)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movieList[position].id == null) {
            TYPE_FOOTER_LOADER
        } else {
            TYPE_MOVIE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_FOOTER_LOADER) {
            val binding =
                LayoutMovieItemFooterLoaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            FooterLoaderItemViewHolder(binding)
        } else {
            val binding =
                LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun getItem(position: Int): Result {
        return movieList[position]
    }

    interface OnAdapterViewClick {
        fun onMovieItemClick(result: Result, imageView: ImageView)
    }

    companion object {
        const val TYPE_FOOTER_LOADER = 1
        const val TYPE_MOVIE_ITEM = 2
    }
}