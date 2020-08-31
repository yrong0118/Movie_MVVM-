package com.yue.moviedb_mvvm.common

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class MovieBasicActivity : AppCompatActivity(),MovieFragmentManager{
    companion object {
        val BUNDLE: String = "bundle"
    }
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    override fun startActivityWithBundle(clazz:Class<*>, isFinished: Boolean, bundle: Bundle) {
        val intent = Intent(this, clazz)
        intent.putExtra(BUNDLE, bundle)
        this.startActivity(intent)
        if(isFinished) {
            finish()
        }
    }

    @LayoutRes
    protected abstract fun getLayout(): Int
}