package com.otb.vipboilerplate.scene.movie.list

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.otb.vipboilerplate.R
import com.otb.vipboilerplate.scene.movie.MovieModels
import com.otb.vipboilerplate.utils.GlideUtils

/**
 * Created by Mohit Rajput on 7/5/19.
 */
class MovieAdapter(private val context: Context, private val movies: List<MovieModels.MovieViewModel>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var onMovieItemClickedListener: OnMovieItemClickedListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_item_movie, viewGroup, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        if (!TextUtils.isEmpty(movie.posterPath)) {
            GlideUtils.loadImage(context, movie.posterPath!!, viewHolder.ivPoster!!, 0)
        }
        if (!TextUtils.isEmpty(movie.title)) {
            viewHolder.tvTitle!!.text = movie.title
        }
        viewHolder.itemView.setOnClickListener { v ->
            if (onMovieItemClickedListener != null) {
                onMovieItemClickedListener!!.onMovieItemClicked(movie)
            }
        }
    }

    fun setOnMovieItemClickedListener(onMovieItemClickedListener: OnMovieItemClickedListener) {
        this.onMovieItemClickedListener = onMovieItemClickedListener
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPoster: ImageView? = itemView.findViewById(R.id.ivPoster)
        val tvTitle: TextView? = itemView.findViewById(R.id.tvTitle)
    }
}