package com.yue.moviedb_mvvm.Adapters

import android.view.View
import android.widget.Button
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.yue.moviedb_mvvm.R

class InternetFailViewHolder(
    @Nullable itemView: View,
    _onMovieListen:OnMovieListen
): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val onMovieListen:OnMovieListen
    lateinit var retryBtn : Button
    init{
        onMovieListen = _onMovieListen
        retryBtn = itemView.findViewById(R.id.retry_button)
        retryBtn.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        onMovieListen.onRetryClick()
    }
}