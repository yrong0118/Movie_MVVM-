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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yue.moviedb_mvvm.Adapters.OnMovieListen
import com.yue.moviedb_mvvm.Adapters.RecyclerViewAdapter
import com.yue.moviedb_mvvm.Models.MovieList

import com.yue.moviedb_mvvm.R
import com.yue.moviedb_mvvm.Utils.Util
import com.yue.moviedb_mvvm.Utils.Util.Companion.printMovies
import com.yue.moviedb_mvvm.ViewModels.MovieListViewModel
import com.yue.moviedb_mvvm.common.MovieBasicFragment

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : MovieBasicFragment(),OnMovieListen{

    companion object {
        val TAG = "MovieListFragment"

        fun newInstance(): MovieListFragment{
            var args = Bundle()
            var fragment = MovieListFragment()
            fragment.arguments = args
            return fragment
        }
    }
        lateinit var goBack: ImageView
        lateinit var titleText: TextView
        lateinit var progressBar: ProgressBar
        lateinit var recyclerView: RecyclerView
        lateinit var mAdapter: RecyclerViewAdapter
        private lateinit var mMovieListViewModel : MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movie_list, container, false)
        initialView(view)
        subscribeObwervers()
        updateMovieListFromViewModel()
        return view
    }

    private fun initialView(view: View) {
        goBack = (this.activity as HomeActivity).findViewById(R.id.ic_go_back)
        titleText = (this.activity as HomeActivity).findViewById(R.id.app_header)
        progressBar = (this.activity as HomeActivity).findViewById(R.id.progress_bar_homepage)
        goBack.visibility = View.GONE
        titleText.text = getString(R.string.app_name)
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycleview_movielist)
        recyclerView.layoutManager = GridLayoutManager(this.context,3)
        mMovieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        mAdapter = RecyclerViewAdapter(this)
        recyclerView.adapter = mAdapter
        mAdapter.disPlayLoading()
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)
        recyclerView.setHasFixedSize(true)
    }

    private fun subscribeObwervers() {
        mMovieListViewModel.getMovieList().observe(this.viewLifecycleOwner, object : Observer<List<MovieList>>{
            override fun onChanged(movies: List<MovieList>?) {
                if(movies != null) {
                    Util.printMovies(movies, TAG)
                    mMovieListViewModel.setRetrieveMovieList(true)
                    mAdapter.setMovies(movies)
                }
            }
        })

        mMovieListViewModel.isMovieListRequestTimeOut().observe(this.viewLifecycleOwner,object :
            Observer<Boolean> {
            override fun onChanged(aBoolean: Boolean) {
                if (aBoolean && ! mMovieListViewModel.didRetrieveMovieList())  {
                    Log.d(TAG,"onChange: timed out")
                    mAdapter.disInternetFailed()
//                    displayErrorScreen("Error retrieving data. check network connection!")
                }
            }
        })

    }

    private fun updateMovieListFromViewModel() {
        val api_key = getString(R.string.movie_db_KEY)
        val primaryReleaseDateGte = Util.getdate(TAG, -15)
        val primaryReleaseDateLte = Util.getdate(TAG, 15)
        val language = getString(R.string.language)
        searchMoviesApi(api_key,primaryReleaseDateGte,primaryReleaseDateLte,language,1)
    }

    private fun searchMoviesApi(api_key: String,primaryReleaseDateGte:String,primaryReleaseDateLte:String,language:String, page:Int) {
        mMovieListViewModel.searchMoviesApi(api_key,primaryReleaseDateGte,primaryReleaseDateLte,language,page)
    }

    override fun onMovieListClick(position: Int) {
        Log.d(TAG,"onMovieList click: clicked $position")
        val bundle = Bundle()
        bundle.putParcelable("movie",mAdapter.getSelectedMovie(position))
        val movieDetailFragment = MovieDetailFragment.newInstance()
        movieDetailFragment.arguments = bundle
        movieFragmentManager.doFragmentTransaction(movieDetailFragment)
    }

    override fun onRetryClick() {
        mAdapter.disPlayLoading()
        updateMovieListFromViewModel()
    }
}
