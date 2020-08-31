package com.yue.moviedb_mvvm.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yue.moviedb_mvvm.Models.Movie
import com.yue.moviedb_mvvm.Repositories.MovieRepository

class MovieDetailViewModel(application: Application): AndroidViewModel(application)  {
    //    private lateinit var mMovies : MutableLiveData<List<MovieList>>

    private var mMovieRepository: MovieRepository = MovieRepository.getInstance()
    private lateinit var mMovieId : String
    private var mDidRetrieveMovie : Boolean = false

    fun getMovie(): LiveData<Movie> {
        return mMovieRepository.getMovie()
    }
    fun isMovieRequestTimeOut(): LiveData<Boolean> {
        return mMovieRepository.isMovieRequestTimeOut()
    }

    fun searchMovieByIdApi(api_key:String,language:String, movieId:String) {
        mMovieId = movieId
        mMovieRepository.searchMovieByIdApi(api_key,language,movieId)
    }
    fun getMovieId () : String {
        return mMovieId
    }

    fun setRetrieveMovie(retrieveMovie: Boolean) {
        mDidRetrieveMovie = retrieveMovie
    }
    fun didRetrieveMovie():Boolean {
        return mDidRetrieveMovie
    }
}