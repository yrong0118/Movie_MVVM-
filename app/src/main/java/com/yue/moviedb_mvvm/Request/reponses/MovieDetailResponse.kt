package com.yue.moviedb_mvvm.Request.reponses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.yue.moviedb_mvvm.Models.Movie
import com.yue.moviedb_mvvm.Models.MovieList

class MovieListResponse() {
    @SerializedName("results")
    @Expose()
    private val results = listOf<MovieList>()

    fun getMovies() : List<MovieList>{
        return results
    }
}

class MovieDetailResponse() {
    @SerializedName("id")
    @Expose()
    private var id = ""
    @SerializedName("title")
    @Expose()
    private var title : String = ""
    @SerializedName("poster_path")
    @Expose()
    private var  imageUrl : String = ""
    @SerializedName("vote_average")
    @Expose()
    private var  rating:String= ""
    @SerializedName("overview")
    @Expose()
    private var  description :String= ""
    @SerializedName("release_date")
    @Expose()
    private var  releaseDate : String = ""

    fun getMovie(): Movie {
        val movie = Movie(title,id,imageUrl,releaseDate,description,rating)
        return movie
    }
}