package com.yue.moviedb_mvvm.common

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {
    companion object{
        private var instance : AppExecutors? = null

        fun getInstance() : AppExecutors {
            if (instance == null) {
                instance = AppExecutors()
            }
            return instance!!
        }
    }


    private val mNetworkIO: ScheduledExecutorService = Executors.newScheduledThreadPool(3)
    fun netWorkIO(): ScheduledExecutorService {
        return mNetworkIO
    }
}