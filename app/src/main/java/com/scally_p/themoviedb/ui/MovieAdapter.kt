package com.scally_p.themoviedb.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scally_p.themoviedb.*
import com.scally_p.themoviedb.data.model.Result
import com.scally_p.themoviedb.databinding.LayoutMovieItemBinding
import com.scally_p.themoviedb.util.Constants
import com.scally_p.themoviedb.util.Utils

class MovieAdapter(private var onAdapterViewClick: OnAdapterViewClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val tag: String = MovieAdapter::class.java.name

    private var movieList = ArrayList<Result>()

    fun setMovieList(movieList: List<Result>) {
        this.movieList = movieList as ArrayList<Result>
        notifyItemRangeChanged(0, movieList.size)
    }

    inner class ItemViewHolder(private val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var result: Result? = null

        fun bind(result: Result) {
            this.result = result

            binding.title.text = result.title
            binding.genre.text = "Action | Comedy | Family"
            binding.releaseDate.text = binding.root.resources.getString(
                R.string.released_on,
                Utils.formatDate(result.release_date.toString())
            )
            binding.ratingBar.rating = result.vote_average.get5StarRating().toFloat()
            Log.d(tag, "get5StarRating - ${result.vote_average.get5StarRating()}")

            binding.rating.text = binding.root.resources.getString(
                R.string.rating,
                result.vote_average.toString(),
                result.vote_count.toString()
            )

            Glide.with(binding.root)
                .load(Constants.Urls.IMAGE + "w500" + result.poster_path)
                .into(binding.poster)

            binding.content.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            when (p0!!.id) {
                R.id.content -> onAdapterViewClick.onMovieItemClick(result!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
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
        fun onMovieItemClick(result: Result)
    }
}