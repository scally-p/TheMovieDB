package com.scally_p.themoviedb.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.scally_p.themoviedb.R
import com.scally_p.themoviedb.data.model.movies.Result
import com.scally_p.themoviedb.ui.main.MoviesViewModel
import com.scally_p.themoviedb.util.Constants
import com.scally_p.themoviedb.util.ImageUtils
import com.scally_p.themoviedb.util.Utils
import org.koin.java.KoinJavaComponent

class SearchAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private val results: List<Result>,
    private var onSearchAdapterViewClick: OnSearchAdapterViewClick
) :
    ArrayAdapter<Result>(
        context,
        layoutResource,
        results
    ),
    Filterable {

    private var mResults: List<Result> = results
    private val moviesViewModel: MoviesViewModel by KoinJavaComponent.inject(MoviesViewModel::class.java)

    override fun getCount(): Int {
        return mResults.size
    }

    override fun getItem(p0: Int): Result {
        return mResults[p0]
    }

    override fun getItemId(p0: Int): Long {
        return mResults[p0].id?.toLong() ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView as ConstraintLayout? ?: LayoutInflater.from(context)
            .inflate(layoutResource, parent, false) as ConstraintLayout

        val result = getItem(position)

        val poster = view.findViewById<ImageView>(R.id.poster)
        val title = view.findViewById<TextView>(R.id.title)
        val genre = view.findViewById<TextView>(R.id.genre)
        val releaseDate = view.findViewById<TextView>(R.id.releaseDate)

        title.text = result.title
        genre.text = moviesViewModel.getMovieGenresString(result.genre_ids)

        releaseDate.text = context.resources.getString(
            R.string.release_date,
            Utils.formatDate(result.release_date.toString())
        )

        ImageUtils.setGlideImage(
            view,
            poster,
            Constants.Urls.IMAGE + "w200" + result.poster_path
        )

        view.setOnClickListener {
            onSearchAdapterViewClick.onSearchMovieItemClick(result, poster)
        }
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: Filter.FilterResults
            ) {
                mResults =
                    filterResults.values as List<com.scally_p.themoviedb.data.model.movies.Result>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    results
                else
                    results.filter {
                        it.title?.toLowerCase()?.contains(queryString) == true ||
                                it.original_title?.toLowerCase()?.contains(queryString) == true
                    }

                return filterResults
            }

        }
    }

    interface OnSearchAdapterViewClick {
        fun onSearchMovieItemClick(result: Result, imageView: ImageView)
    }
}