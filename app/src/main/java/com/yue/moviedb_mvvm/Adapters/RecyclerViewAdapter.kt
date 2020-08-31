package com.yue.moviedb_mvvm.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yue.moviedb_mvvm.Models.MovieList
import com.yue.moviedb_mvvm.R
import com.yue.moviedb_mvvm.Utils.Constants
import com.yue.moviedb_mvvm.Utils.Util

class RecyclerViewAdapter(val onMovieListen:OnMovieListen): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private val MOVIE_LIST_TYPE = 1
        private val LOADING_TYPE = 2
        private val INTERNET_FAIL_TYPE = 3
    }

    var mData: List<MovieList> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var mInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var view: View? = null
        when (viewType) {
            MOVIE_LIST_TYPE -> {
                view = mInflater.inflate(R.layout.card_view_movie_item,parent,false)
                return  MovieListViewHolder(view, onMovieListen)
            }
            LOADING_TYPE-> {
                view= mInflater.inflate(R.layout.layout_loading_list_item, parent, false)
                return LoadingViewHolder(view)
            }
            INTERNET_FAIL_TYPE -> {
                view= mInflater.inflate(R.layout.layout_internet_fail, parent, false)
                return InternetFailViewHolder(view,onMovieListen)
            }
            else -> {
                view= mInflater.inflate(R.layout.card_view_movie_item, parent, false)
                return MovieListViewHolder(view , onMovieListen)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == MOVIE_LIST_TYPE) {
            val currentMovies = mData[position]
            if (!Util.isStringEmpty(currentMovies.imageUrl)) {
                Picasso
                    .get()
                    .load(Constants.img_front_path + mData.get(position).imageUrl)
                    .into((holder as MovieListViewHolder).movieImg)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (mData.get(position).title.equals(Constants.LOADING)) {
            return LOADING_TYPE
        } else if (mData.get(position).title.equals(Constants.RETRY)) {
            return INTERNET_FAIL_TYPE
        } else {
            return MOVIE_LIST_TYPE
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun disPlayLoading() {
        if (!isLoading()) {
            val loading = MovieList()
            loading.title = Constants.LOADING
            val loadingList = arrayListOf<MovieList>()
            loadingList.add(loading)
            mData = loadingList
            notifyDataSetChanged()
        }
    }

    private fun isLoading():Boolean {
        if (mData != null) {
            if (mData.size > 0) {
                if (mData.get(mData.size - 1).title.equals(Constants.LOADING)) {
                    return true;
                }
            }
        }
        return false
    }

    fun setMovies(movies:List<MovieList>) {
        mData =  movies
        notifyDataSetChanged()
    }

    fun getSelectedMovie(position: Int):MovieList? {
        if (mData != null) {
            if (mData.size > 0) {
                return mData.get(position)
            }
        }
        return null
    }

    fun disInternetFailed(){
        if (!isInternetFailed()) {
            val retry = MovieList()
            retry.title = Constants.RETRY
            val retryList = arrayListOf<MovieList>()
            retryList.add(retry)
            mData = retryList
            notifyDataSetChanged()
        }
    }

    private fun isInternetFailed():Boolean{
        if (mData != null) {
            if (mData.size > 0) {
                if (mData.get(mData.size - 1).title.equals(Constants.RETRY)) {
                    return true
                }
            }
        }
        return false
    }
}