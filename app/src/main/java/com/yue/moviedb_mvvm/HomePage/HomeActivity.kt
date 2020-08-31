package com.yue.moviedb_mvvm.HomePage

import android.os.Bundle
import android.widget.ProgressBar
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yue.moviedb_mvvm.R
import com.yue.moviedb_mvvm.common.MovieBasicActivity
import com.yue.moviedb_mvvm.common.MovieBasicFragment
import com.yue.moviedb_mvvm.Utils.Util

class HomeActivity : MovieBasicActivity() {
    lateinit var bottomBar: BottomNavigationView
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieListFragment = MovieListFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.container, movieListFragment).commit()

        bottomBar = findViewById(R.id.bottomBar)
        progressBar = findViewById(R.id.progress_bar_homepage)
        bottomBar.selectedItemId = R.id.action_find
        Util.showProgressBar(progressBar, false)
        bottomBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_find -> {

                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    override fun getLayout(): Int {
        return R.layout.activity_home
    }

    override fun doFragmentTransaction(movieBasicFragment: MovieBasicFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, movieBasicFragment)
            .addToBackStack(movieBasicFragment.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
