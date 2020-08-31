package com.yue.moviedb_mvvm.Utils

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.yue.moviedb_mvvm.Models.MovieList
import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        @JvmStatic
        fun getdate(tag:String,day:Int): String {
            //2020-01-15
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            //Getting current date
            val cal = Calendar.getInstance()
            //Number of Days to add/substract
            cal.add(Calendar.DAY_OF_MONTH, day)
            //Date after adding/subtract the days to the current date
            val newDate = sdf.format(cal.getTime())
            //Displaying the new Date after addition of Days to current date
            Log.d(tag,"Date after Addition: $newDate")
            return newDate
        }

        fun showProgressBar(mProgressBar: ProgressBar, visibility:Boolean) {
            if (visibility) {
                mProgressBar.visibility = View.VISIBLE
            } else {
                mProgressBar.visibility = View.INVISIBLE
            }
        }


        fun isStringEmpty(str:String?):Boolean{
            return (str == null || str.length == 0)
        }

        fun printMovies(list : List<MovieList>, tag:String) {
            for (cur   in 0 until list.size) {
                Log.d(tag,"----------onchange: ${list.get(cur).id}----------")
            }
        }
        fun printMovie(description : String, tag:String) {
            Log.d(tag,"-----------onchange: ${description}--------------")
        }
    }
}