package com.yue.moviedb_mvvm.Request

import com.yue.moviedb_mvvm.Utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {
    companion object {
        private val retrofitBuilder : Retrofit.Builder =
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
        private val retrofit : Retrofit = retrofitBuilder.build()
        private val movieApi = retrofit.create(MovieApi::class.java)
        fun getMovieApi() : MovieApi {
            return movieApi
        }
    }
}