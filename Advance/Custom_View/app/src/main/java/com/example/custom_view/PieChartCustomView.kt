package com.example.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.provider.SyncStateContract.Constants
import android.renderscript.Sampler
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View


class PieChartCustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var size = 600
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size , size)
        Log.d("tag","width = $measuredWidth, height = $measuredHeight ")
    }
    private var start_angle = 270f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectF = RectF(-size.toFloat(), -size.toFloat(), size.toFloat(), size.toFloat())
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        canvas.translate(width / 2f, height / 2f)
        for(i in 1..5){
            colorList[i-1].also { paint.color = it }
            canvas.drawArc(mRectF, start_angle, sweepList[i-1], true, paint)
            start_angle += sweepList[i-1]
        }
    }
}