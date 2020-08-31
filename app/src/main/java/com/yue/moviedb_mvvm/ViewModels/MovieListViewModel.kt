package com.yue.moviedb_mvvm.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yue.moviedb_mvvm.Models.MovieList
import com.yue.moviedb_mvvm.Repositories.MovieRepository

class MovieListViewModel(application: Application): AndroidViewModel(application) {
    private var mDidRetrieveMovieList : Boolean = false
    private var mMovieRepository: MovieRepository = MovieRepository.getInstance()

    fun getMovieList(): LiveData<List<MovieList>> {
        return mMovieRepository.getMovies()
    }

    fun searchMoviesApi(api_key:String,primaryReleaseDateGte:String,primaryReleaseDateLte:String,language:String, page:Int) {
        mMovieRepository.searchMoviesApi(api_key,primaryReleaseDateGte,primaryReleaseDateLte,language,page)
    }


    fun isMovieListRequestTimeOut(): LiveData<Boolean> {
        return mMovieRepository.isMovieListRequestTimeOut()
    }


    fun setRetrieveMovieList(retrieveMovieList: Boolean) {
        mDidRetrieveMovieList = retrieveMovieList
    }

    fun didRetrieveMovieList():Boolean {
        return mDidRetrieveMovieList
    }


}