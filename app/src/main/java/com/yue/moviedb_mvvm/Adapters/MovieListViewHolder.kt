package com.yue.moviedb_mvvm.Adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.yue.moviedb_mvvm.R

class MovieListViewHolder(
    @Nullable itemView : View,
    _onMovieListen:OnMovieListen
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val movieTitle : TextView
    val movieImg : ImageView
    val onMovieListen : OnMovieListen

    init{
        movieTitle= itemView.findViewById(R.id.movie_title) as TextView
        movieImg = itemView.findViewById(R.id.movie_img) as ImageView
        onMovieListen = _onMovieListen
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onMovieListen.onMovieListClick(adapterPosition)
    }
}