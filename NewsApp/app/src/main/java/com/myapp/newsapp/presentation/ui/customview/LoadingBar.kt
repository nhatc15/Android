package com.myapp.newsapp.presentation.ui.customview

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.myapp.newsapp.R


class LoadingBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val blue = ContextCompat.getColor(context,R.color.mainColor)
    private val loading_bar_color = ContextCompat.getColor(context,R.color.loading_bar_color)
    private var progress: Int = 0
    private var valueAnimator: ValueAnimator
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textSize = 25f * resources.displayMetrics.density

    private val updateListener = ValueAnimator.AnimatorUpdateListener {
        progress = (it.animatedValue as Float).toInt()
        invalidate()
    }

    init {
        valueAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.loading_bar
        ) as ValueAnimator

        valueAnimator.addUpdateListener(updateListener)
        valueAnimator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paintBorder.style = Paint.Style.FILL
        paintBorder.color = loading_bar_color
        canvas?.drawRoundRect(width * 0.09f, height * 0.695f, width * 0.91f,height * 0.705f+textSize+10f,20f,20f, paintBorder )


        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas?.drawRoundRect(width * 0.1f, height * 0.7f, width * 0.8f * progress/100 + width * 0.1f,height * 0.7f+textSize+10f,20f,20f, paint )

        paintText.style = Paint.Style.FILL
        paintText.color = blue
        paintText.textAlign = Paint.Align.CENTER
        paintText.textSize = textSize
        canvas?.drawText("${progress}%",width * 0.5f,height*0.73f,paintText)
    }
}