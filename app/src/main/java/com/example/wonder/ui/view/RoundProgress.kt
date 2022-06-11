package com.example.wonder.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.wonder.R


class RoundProgress(context: Context, attrs: AttributeSet) :
    View(context, attrs) {
    //画笔对象的引用
    private var paint: Paint = Paint()

    //圆环的颜色
    private var roundColor = 0

    //圆环进度的颜色
    private var roundProgressColor = 0

    //圆环的宽度
    private var roundWidth = 0f

    //最大进度
    private var max = 0

    //当前进度
    private var progress = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //画最外层大圆环
        val centerX = width / 2 //获取中心点X坐标
        val certerY = height / 2 //获取中心点Y坐标
        val radius = (centerX - roundWidth / 2).toInt() //圆环的半径
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = roundColor
        paint.style = Paint.Style.STROKE //设置只描边
        paint.strokeWidth = roundWidth //设置圆环宽度
        paint.isAntiAlias = true //消除锯齿
        canvas.drawCircle(centerX.toFloat(), certerY.toFloat(), radius.toFloat(), paint) //绘制圆环


        //画进度
        paint.strokeWidth = roundWidth //设置圆环宽度
        paint.color = roundProgressColor //设置进度颜色
        //用于定义的圆弧的形状和大小的界限
        val oval = RectF(
            (centerX - radius).toFloat(), (centerX - radius).toFloat(), (centerX
                    + radius).toFloat(), (centerX + radius).toFloat()
        )
        canvas.drawArc(
            oval,
            (-90).toFloat(),
            (360 * progress / max).toFloat(),
            false,
            paint
        ) // 根据进度画圆弧

    }

    fun setRoundColor(roundColor: Int) {
        this.roundColor = roundColor
    }

    fun setRoundProgressColor(roundProgressColor: Int) {
        this.roundProgressColor = roundProgressColor
    }

    fun setRoundWidth(roundWidth: Float) {
        this.roundWidth = roundWidth
    }

    @Synchronized
    fun setMax(max: Int) {
        require(max >= 0) { "最大值为0" }
        this.max = max
    }

    @Synchronized
    fun setProgress(progress: Int) {
        require(progress >= 0) { "进度小于0" }
        if (progress > max) {
            this.progress = progress
        }
        if (progress <= max) {
            this.progress = progress
            postInvalidate()
        }
    }

    init {
        //从attrs.xml中获取自定义属性和默认值
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RoundProgress)
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.WHITE) //外边框的颜色
        roundProgressColor =
            typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.WHITE) //加载进度颜色
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, 5f) //圆环宽度
        max = typedArray.getInteger(R.styleable.RoundProgress_max, 100) //最大
        typedArray.recycle()
    }
}