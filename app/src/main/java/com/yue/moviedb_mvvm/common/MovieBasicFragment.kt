package com.yue.moviedb_mvvm.common

import android.content.Context
import androidx.fragment.app.Fragment

open class MovieBasicFragment: Fragment() {
    protected lateinit var movieFragmentManager: MovieFragmentManager
    companion object {
        private val uuid: String = java.util.UUID.randomUUID().toString()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        movieFragmentManager = context as MovieFragmentManager
    }
}