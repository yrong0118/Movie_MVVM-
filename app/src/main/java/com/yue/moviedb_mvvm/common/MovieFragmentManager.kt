package com.yue.moviedb_mvvm.common

import android.content.IntentSender
import android.os.Bundle

interface MovieFragmentManager {

    fun doFragmentTransaction(movieBasicFragment: MovieBasicFragment)

    fun startActivityWithBundle(
        clazz: Class<*>,
        isFinished: Boolean,
        bundle: Bundle
    )

}