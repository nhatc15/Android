package com.example.assignment_day06_animation

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.assignment_day06_animation.Data.columnValue
import com.example.assignment_day06_animation.Data.columnValueDetailList
import com.example.assignment_day06_animation.Data.days
import com.example.assignment_day06_animation.Data.titleList
import com.example.assignment_day06_animation.Data.valueList
import com.example.assignment_day06_animation.Data.valuePercent

class CustomView(
    context: Context,
    attr: AttributeSet
) : View(context, attr) {
    private val dark_blue = ContextCompat.getColor(context,R.color.dark_blue)
    private val grey = ContextCompat.getColor(context, R.color.grey)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintColumn = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintValueBox = Paint(Paint.ANTI_ALIAS_FLAG)

    val iconElectric = ResourcesCompat.getDrawable(resources, R.drawable.ic_electric, null)
        ?.toBitmap(24f.getDP().toInt(), 24f.getDP().toInt())
    val iconInternet = ResourcesCompat.getDrawable(resources, R.drawable.ic_internet, null)
        ?.toBitmap(24f.getDP().toInt(), 24f.getDP().toInt())
    val iconSuperMarket = ResourcesCompat.getDrawable(resources, R.drawable.ic_super_market, null)
        ?.toBitmap(24f.getDP().toInt(), 24f.getDP().toInt())

    val iconChrome = ResourcesCompat.getDrawable(resources, R.drawable.ic_chrome, null)
        ?.toBitmap(15f.getDP().toInt(), 15f.getDP().toInt())
    val iconFacebook = ResourcesCompat.getDrawable(resources, R.drawable.ic_facebook, null)
        ?.toBitmap(15f.getDP().toInt(), 15f.getDP().toInt())
    val iconFireFox = ResourcesCompat.getDrawable(resources, R.drawable.ic_firefox, null)
        ?.toBitmap(15f.getDP().toInt(), 15f.getDP().toInt())

    val iconPhone = ResourcesCompat.getDrawable(resources, R.drawable.ic_phone, null)
        ?.toBitmap(36f.getDP().toInt(), 36f.getDP().toInt())

    val supermarket_color = R.color.super_market_spending_color
    val electric_color = R.color.electric_spending_color
    val internet_color = R.color.internet_spending_color

    val chrome_color = R.color.chrome_activity_color
    val facebook_color = R.color.facebook_activity_color
    val firefox_color = R.color.firefox_activity_color

    val iconList = listOf(iconElectric, iconInternet, iconSuperMarket, iconChrome, iconFacebook, iconFireFox)
    val colorList = listOf(supermarket_color, electric_color, internet_color, chrome_color, facebook_color,firefox_color)

    private var indexColumn = -1
    private var chartColumns = mutableListOf<RectF>()
    private var rectF = RectF()

    @Volatile
    private var progress: Double = 0.0
    private var columnProgress: Double = 0.0
    private var columnColor: Int = grey
    private var textColor: Int = grey
    private var valueBoxColor: Int = dark_blue

    private var valueAnimator: ValueAnimator
    private var columnValueAnimator: ValueAnimator
    private var columnColorAnimator: ValueAnimator
    private var textColorAnimator: ValueAnimator
    private var valueBoxColorAnimator: ValueAnimator

    private val updateListener = ValueAnimator.AnimatorUpdateListener {
        progress = (it.animatedValue as Float).toDouble()
        invalidate()
    }

    private val columnUpdateListener = ValueAnimator.AnimatorUpdateListener {
        columnProgress = (it.animatedValue as Float).toDouble()
        invalidate()
    }

    init {
        Log.d("tag","init: $width, $height")
        isClickable = true

        valueAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.loading_animation
        ) as ValueAnimator

        valueAnimator.addUpdateListener(updateListener)

        columnValueAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.loading_column
        ) as ValueAnimator
        columnValueAnimator.addUpdateListener(columnUpdateListener)
        columnValueAnimator.start()


        columnColorAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.column_color_animator
        ) as ValueAnimator
        columnColorAnimator.addUpdateListener {
            columnColor = (it.animatedValue as Int).toInt()
            invalidate()
        }

        textColorAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.text_color_animator
        ) as ValueAnimator
        textColorAnimator.addUpdateListener {
            textColor = (it.animatedValue as Int).toInt()
            invalidate()
        }

        valueBoxColorAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.value_box_color_animator
        ) as ValueAnimator
        valueBoxColorAnimator.addUpdateListener {
            valueBoxColor = (it.animatedValue as Int).toInt()
            invalidate()
        }
        // initialize custom attributes of the button
        val attributeSet = context.obtainStyledAttributes(R.styleable.Column)
        try {

        } finally {
            attributeSet.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        drawFirstChart(canvas)
        if(indexColumn != -1) drawSecondChart(canvas)
        drawThirdChart(canvas)
        Log.d("tag","onDraw")
        super.onDraw(canvas)
    }

    //Part 1
    fun drawFirstChart(canvas: Canvas) {
        //Background
        drawCustomRect(canvas, width * 0.05f, height * 0.04f, width * 0.95f, height * 0.52f, 40f, 40f, R.color.dark_blue)
        //Text
        drawCustomText(canvas, "Your Money", Color.WHITE, width * 0.92f, height * 0.08f, 18f, Align.RIGHT)
        drawCustomText(canvas, "Your spending in daily life", Color.GRAY, width * 0.92f, height * 0.1f, 10f, Align.RIGHT)
        drawCustomText(canvas, "7 DAYS", Color.WHITE, width * 0.10f, height * 0.12f, 8f, Align.LEFT)
        drawCustomText(canvas, "LAST WEEK", Color.GRAY, width * 0.20f, height * 0.12f, 8f, Align.LEFT)
        drawCustomText(canvas, "Total", Color.GRAY, width * 0.10f, height * 0.145f, 8f, Align.LEFT)
        drawCustomText(canvas, "5 550 000 VND", Color.WHITE, width * 0.10f, height * 0.165f, 11f, Align.LEFT)
        //Line
        canvas.drawLine(width * 0.10f, height * 0.13f, width * 0.91f, height * 0.13f, paint.apply { color = Color.GRAY})
        canvas.drawLine(width * 0.10f, height * 0.13f, width * 0.17f, height * 0.13f, paint.apply { color = Color.WHITE })
        //Column
        for (i in 0..6) {
            drawCustomColumn(
                canvas,
                left = width * 0.11f + i * width * 0.12f,
                top = height * 0.48f,
                width * 0.06f, -(height * 0.21f / 100 * Data.columnValue[i])* (columnProgress/100).toFloat(),
                days[i],
                columnValueDetailList[i],
                columnIndex = i
            )
            rectF = RectF(
                width * 0.11f + i * width * 0.12f,
                height * 0.48f -(height * 0.21f / 100 * Data.columnValue[i]),
                width * 0.11f + i * width * 0.12f + width * 0.06f,
                height * 0.48f
            )
            if(chartColumns.size < 7) chartColumns.add(rectF)
        }
    }

    fun drawCustomColumn(
        canvas: Canvas,
        left: Float, top: Float,
        widthLine: Float, heightLine: Float,
        text: String,
        textColumnValue:String,
        columnIndex: Int = -1
    ) {
        paintColumn.color = if (columnIndex == indexColumn) columnColor else grey
        paintColumn.style = Paint.Style.FILL

        paintValueBox.color = if (columnIndex == indexColumn) valueBoxColor else dark_blue
        paintValueBox.style = Paint.Style.FILL

        canvas.apply {
            drawRoundRect(left, top + heightLine, left + widthLine, top, 10f, 10f, paintColumn)
            drawRoundRect(left, top + heightLine -width*0.07f, left + widthLine, top + heightLine -width*0.02f, 20f, 20f, paintValueBox )
            drawCustomText(
                this,
                text,
                colorCustom = if (indexColumn == columnIndex) textColor else grey,
                left + width * 0.03f, top * 1.05f,
                7f,
                Align.CENTER
            )
            drawCustomText(
                this,
                textColumnValue,
                colorCustom = if (indexColumn == columnIndex) textColor else dark_blue,
                left + width * 0.03f, top + heightLine -width*0.04f,
                5f,
                Align.CENTER
            )
        }
    }



    //Part 2
    private fun drawSecondChart(canvas: Canvas) {
        for(i in 0..2){
            iconList[i]?.let { drawBitmap(canvas, it, width * 0.13f, height * 0.56f + height * 0.05f * i, null) }
            drawCustomText(canvas, titleList[i], Color.BLACK, width * 0.205f, height * 0.585f+ height*0.05f*i, 8f, Align.LEFT)
            drawCustomRect(canvas, width * 0.35f, height * 0.575f+height*0.05f*i, width * 0.35f+ width*0.55f * valueList[i]* (progress/100).toFloat(), height * 0.577f + height*0.05f*i + 15f, 10f, 10f, colorList[i])
            drawCustomText(canvas, "${progress.toInt()* valueList[i]}%",grey,width * 0.35f+ width*0.55f * valueList[i]* (progress/100).toFloat(),height * 0.583f + height * 0.05f * i,8f,Align.LEFT)
        }
    }



    //Part 3
    private fun drawThirdChart(canvas: Canvas) {

        //Background
        drawCustomRect(canvas, width * 0.05f, height * 0.74f, width * 0.95f, height * 0.96f, 40f, 40f, R.color.gray)
        //Title
        drawCustomText(canvas, "Today activity", Color.WHITE, width * 0.12f, height * 0.785f, 18f, Align.LEFT)
        drawCustomText(canvas, "Spend time on smart phone", Color.GRAY, width * 0.12f, height * 0.8f, 10f, Align.LEFT)
        iconPhone?.let { drawBitmap(canvas, it, width * 0.817f, height * 0.76f, null) }

        for(i in 0..2){
//            drawItems(canvas, titleList[i+3], iconList[i+3], colorList[i+3],i)
            drawCustomText(canvas, titleList[i+3], Color.WHITE, width * 0.18f, height * (0.84f + i*0.04f), 12f, Align.LEFT)
            iconList[i+3]?.let { drawBitmap(canvas, it, width * 0.13f, height * (0.825f + i*0.04f), null) }
            drawCustomRect(canvas, width * 0.35f, height * (0.83f + i*0.04f), width * valueList[i+3], height * (0.83f+i*0.04f) + 15f, 10f, 10f, colorList[i+3])
            drawCustomText(canvas, "${valuePercent[i]}%", Color.GRAY, width * (valueList[i+3] + 0.01f), height * (0.84f + i*0.04f), 11f, Align.LEFT)
        }
    }

    //Custom
    private fun drawBitmap(
        canvas: Canvas,
        icon: Bitmap,
        left: Float,
        top: Float,
        paint: Paint?
    ) {
        canvas.drawBitmap(icon, left, top, paint)
    }


    private fun drawCustomRect(
        canvas: Canvas,
        left: Float, top: Float, right: Float, bottom: Float,
        rx: Float, ry: Float,
        color: Int
    ) {
        paint.color = ContextCompat.getColor(context, color)
        canvas.drawRoundRect(
            left, top, right, bottom, rx, ry,
            paint
        )
    }

    private fun drawCustomText(
        canvas: Canvas,
        text: String,
        colorCustom: Int = Color.WHITE,
        posX: Float,
        posY: Float,
        textSizeCustom: Float,
        align: Align
    ) {
        paintText.apply {
            color = colorCustom
            textSize = textSizeCustom.getDP()
            textAlign = align
        }
        canvas.drawText(text, posX, posY, paintText)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {
            val x = event.x
            val y = event.y

            Log.d("tag", "onTouchEvent: touch ($x, $y)")

            chartColumns.forEachIndexed { index, rectF ->
                if (rectF.contains(x,y)) {
                    indexColumn = index
                    valueAnimator.start()
                    columnColorAnimator.start()
                    textColorAnimator.start()
                    valueBoxColorAnimator.start()
                }
            }
        }
        return super.onTouchEvent(event)
    }


    private fun Float.getDP(): Float {
        return this * resources.displayMetrics.density
    }
}