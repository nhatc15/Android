package com.example.loadingspinnercustomview

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class LoadingIndicator(context: Context, attr: AttributeSet): View(context,attr) {

    private var size = 640

    @Volatile
    private var valueAnimator: ValueAnimator
    private var progress: Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size= measuredWidth.coerceAtMost(measuredHeight)
        setMeasuredDimension(size,size)
    }

    private val updateListener = ValueAnimator.AnimatorUpdateListener {
        progress  = (it.animatedValue as Int).toInt()
        invalidate()
        requestLayout()
    }

    init {
        valueAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.loading_animation
        ) as ValueAnimator
        valueAnimator.addUpdateListener(updateListener)
        valueAnimator.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rect = RectF(size * 0.49f, size * 0.39f, size * 0.51f, size * 0.44f)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.LTGRAY
        paint.style = Paint.Style.FILL

        for (i in 0..360 step 30) {
            canvas.save()
            paint.color = when {
                i / 30 == progress -> Color.GRAY
                i / 30 == progress + 1 -> Color.DKGRAY
                i / 30 == progress + 2 -> Color.BLACK
                else -> Color.LTGRAY
            }
            canvas.rotate(0f + i, size * 0.5f, size * 0.5f)
            canvas.drawRect(rect, paint)
            canvas.restore()
        }
    }
}