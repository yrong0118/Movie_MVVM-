package com.yue.moviedb_mvvm.HomePage


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.yue.moviedb_mvvm.Models.Movie
import com.yue.moviedb_mvvm.Models.MovieList

import com.yue.moviedb_mvvm.R
import com.yue.moviedb_mvvm.Utils.Constants
import com.yue.moviedb_mvvm.Utils.Util
import com.yue.moviedb_mvvm.ViewModels.MovieDetailViewModel
import com.yue.moviedb_mvvm.common.MovieBasicFragment

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment :  MovieBasicFragment() {
    lateinit var goBack: ImageView
    lateinit var titleText: TextView
    lateinit var progressBar: ProgressBar
    var currMovie: MovieList? = null
    lateinit var movieTitleDetail: TextView
    lateinit var movieBGImg : ImageView
    lateinit var movieImg: ImageView
    lateinit var movieRating: TextView
    lateinit var movieDescription: TextView
    lateinit var movieFav: ImageView
    private lateinit var mMovieDetailViewModel : MovieDetailViewModel

    var uid :String? = ""

    companion object{
        val TAG = "MovieDetailFragment"

        fun newInstance(): MovieDetailFragment {
            var args = Bundle()
            var fragment = MovieDetailFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movie_detail, container, false)
        val bundle = this.arguments

        if (bundle != null) {
            currMovie= bundle.getParcelable<MovieList>("movie")!!
        }

        initialView(view)
        subscribeObservers()
        updateMovieFromViewModel()
        return view
    }
    private fun subscribeObservers() {
        mMovieDetailViewModel.getMovie().observe(this.viewLifecycleOwner, object :
            Observer<Movie> {
            override fun onChanged(movie: Movie?) {

                if (movie != null) {
                    //这个if解决了如果点开不同的movie detail page 不显示loading的问题，因为viewmodel不随着activity的毁坏而毁坏
                    //查看如果当前的id与viewmodel中的相同，说明就是新的movie detail，要加载loading
                    if (movie.id.equals(mMovieDetailViewModel.getMovieId())) {
                        setMovie(movie)
                        mMovieDetailViewModel.setRetrieveMovie(true)
                    }
                    Util.printMovie(movie.description, TAG)

                }


            }
        })

        mMovieDetailViewModel.isMovieRequestTimeOut().observe(this.viewLifecycleOwner,object :
            Observer<Boolean> {
            override fun onChanged(aBoolean: Boolean) {
                if (aBoolean && ! mMovieDetailViewModel.didRetrieveMovie())  {
                    Log.d(TAG,"onChange: timed out")
                    displayErrorScreen("Error retrieving data. check network connection!")
                }
            }
        })
    }

    private fun initialView(view: View) {
        goBack = (this.activity as HomeActivity).findViewById(R.id.ic_go_back)
        titleText = (this.activity as HomeActivity).findViewById(R.id.app_header)
        progressBar = (this.activity as HomeActivity).findViewById(R.id.progress_bar_homepage)
        goBack.visibility = View.VISIBLE

        movieTitleDetail = view.findViewById(R.id.movie_title_detailpage)
        movieBGImg = view.findViewById(R.id.movie_img_background_detailpage)
        movieImg = view.findViewById(R.id.movie_img_detailpage)
        movieRating = view.findViewById(R.id.movie_rating_detailpage)
        movieDescription = view.findViewById(R.id.movie_detail_description)
        movieFav = view.findViewById(R.id.movie_liked)

        titleText.text = currMovie!!.title
        movieTitleDetail.text = currMovie!!.title

        if (currMovie!!.imageUrl != ""){
            Picasso
                .get()
                .load(Constants.img_front_path + currMovie!!.imageUrl)
                .placeholder(R.drawable.no_images_available)
                .into(movieBGImg)

            Picasso
                .get()
                .load(Constants.img_front_path + currMovie!!.imageUrl)
                .placeholder(R.drawable.no_images_available)
                .into(movieImg)
        }

        mMovieDetailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

        Util.showProgressBar(progressBar,true)

        goBack.setOnClickListener{
            requireFragmentManager().popBackStack()
        }
    }

    fun setMovie(movie:Movie) {
        movieDescription.text = movie.description
        movieRating.text = movie.rating
//        movieRating.text = movie.rating

        Util.showProgressBar(progressBar,false)

    }

    private fun updateMovieFromViewModel() {
        val api_key = getString(R.string.movie_db_KEY)
        val language = getString(R.string.language)
        searchMovieApi(api_key,language,currMovie!!.id)
    }

    private fun searchMovieApi(api_key: String,language:String,movieId: String) {
        mMovieDetailViewModel.searchMovieByIdApi(api_key,language,movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun displayErrorScreen(errorMessage:String) {

        movieTitleDetail.text = "Error retrieving movie..."
        movieDescription.text = "Error retrieving movie..."
        movieRating.text = "0"
        if (!errorMessage.equals("")) {
            movieDescription.text = errorMessage
        } else {
            movieDescription.text = "Error retrieving movie..."
        }
        Util.showProgressBar(progressBar, false)
    }
}
