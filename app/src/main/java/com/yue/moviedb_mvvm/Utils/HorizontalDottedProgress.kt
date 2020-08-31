package com.yue.moviedb_mvvm.Utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation

class HorizontalDottedProgress(context: Context) : View(context) {
    // actual dot radius
    private var mDotRadius = 5

    // Bounced Dot Radius
    private var mBigDotRadius = 8

    // to get identified in which position dot has to bounce
    private var mDotPosition = 0
    private val mDotCount = 10


    constructor(context: Context, attrs: AttributeSet) : this(context) {
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int):this(context,attrs){

    }

    //Method to draw your customized dot on the canvas
    override  fun onDraw(canvas: Canvas) {
        super.onDraw(canvas);
        val paint: Paint = Paint()
        paint.color = Color.parseColor("#fd853f")
        createDots(canvas, paint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation()
    }

    private fun createDots(canvas: Canvas, paint: Paint) {

        for (i in 0 until mDotCount) {
            val cx = (10 + (i * 20))
            if (i == mDotPosition) {
                canvas.drawCircle(
                    cx.toFloat(),
                    mBigDotRadius.toFloat(),
                    mBigDotRadius.toFloat(),
                    paint
                )
            } else {
                canvas.drawCircle(
                    cx.toFloat(),
                    mBigDotRadius.toFloat(),
                    mDotRadius.toFloat(),
                    paint
                )
            }
        }
    }

    override protected fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec:Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        val calculatedWith = (20 * 9)
        val width = calculatedWith
        val height = (mBigDotRadius * 2)
        // MUST CALL THIS
        setMeasuredDimension(width,height);
    }

    private fun startAnimation() {
        val bounceAnimation : BounceAnimation  = BounceAnimation()
        bounceAnimation.setDuration(100)
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(LinearInterpolator())
        bounceAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation : Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
                mDotPosition++
                if ( mDotPosition == mDotCount) {
                    mDotPosition = 0;
                }
                Log.d("INFOMETHOD","-----------ON Animation Repeat --------- ")
            }
        })
        startAnimation(bounceAnimation);
    }


    private inner class BounceAnimation : Animation() {
        override fun applyTransformation(
            interpolatedTime:Float ,
            t: Transformation
        ) {
            super.applyTransformation(interpolatedTime, t);
            // call invalidate to redraw your view again
            invalidate()
        }
    }

}