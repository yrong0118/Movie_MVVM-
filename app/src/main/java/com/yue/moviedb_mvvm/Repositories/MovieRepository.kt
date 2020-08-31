package com.yue.moviedb_mvvm.Repositories

import androidx.lifecycle.LiveData
import com.yue.moviedb_mvvm.Models.Movie
import com.yue.moviedb_mvvm.Models.MovieList
import com.yue.moviedb_mvvm.Request.MovieApiClient

class MovieRepository() {
    private lateinit var mMovieApiClient: MovieApiClient
    companion object {
        private val TAG = "MovieRepository"
        private var instance : MovieRepository?=null
        fun getInstance():MovieRepository{
            if (instance == null) {
                instance = MovieRepository()
            }
            return instance!!
        }
    }

    fun getMovies(): LiveData<List<MovieList>> {
        mMovieApiClient = MovieApiClient.getInstance()
        return mMovieApiClient.getMovies()
    }

    fun searchMoviesApi(api_key:String,primaryReleaseDateGte:String,primaryReleaseDateLte:String,language:String, _page:Int) {
        var page = _page
        if (page == 0) page = 1
        mMovieApiClient.searchMoviesApi(api_key,primaryReleaseDateGte,primaryReleaseDateLte,language,page)
    }

    fun isMovieListRequestTimeOut(): LiveData<Boolean> {
        return mMovieApiClient.isMovieListRequestTimeOut()
    }


    fun getMovie():LiveData<Movie> {
        mMovieApiClient = MovieApiClient.getInstance()
        return mMovieApiClient.getMovie()
    }
    fun isMovieRequestTimeOut(): LiveData<Boolean> {
        return mMovieApiClient.isMovieRequestTimeOut()
    }

    fun searchMovieByIdApi(api_key:String,language:String, movieId:String) {
        mMovieApiClient.searchMovieByIdApi(api_key,language,movieId)
    }
}