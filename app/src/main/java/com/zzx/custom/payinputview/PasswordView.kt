package com.zzx.custom.payinputview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PasswordView : View {
    private var itemWidth = 0f
    private var rectF = RectF()
    private var passwordPaint = Paint()
    private var strokePaint = Paint()
    private var password = StringBuilder()

    private var passwordColor = Color.BLACK
    private var strokeColor = Color.BLACK
    private var strokeWidth = 3f
    private var passwordSize = 30f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attributeSet,
            defStyleAttr
    ) {
        init(attributeSet)
    }

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView)
        passwordColor = typedArray.getColor(R.styleable.PasswordView_password_color, Color.BLACK)
        strokeColor = typedArray.getColor(R.styleable.PasswordView_stroke_color, Color.BLACK)
        strokeWidth = typedArray.getDimension(R.styleable.PasswordView_stroke_width, 3f)
        passwordSize = typedArray.getDimension(R.styleable.PasswordView_password_size, 30f)
        typedArray.recycle()
        strokePaint.color = strokeColor
        strokePaint.isAntiAlias = true
        strokePaint.strokeWidth = strokeWidth
        strokePaint.style = Paint.Style.STROKE

        passwordPaint.color = passwordColor
        passwordPaint.isAntiAlias = true
        passwordPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (width == 0) {
            return
        }
        itemWidth = width / 6f
        rectF.set(0f, 0f, width.toFloat(), itemWidth)
        canvas?.drawRoundRect(rectF, 8f, 8f, strokePaint)
        repeat(5) {
            val x = itemWidth * (it + 1)
            canvas?.drawLine(x, 0f, x, itemWidth, strokePaint)
        }

        repeat(password.length) {
            val x = itemWidth / 2 + itemWidth * it
            canvas?.drawCircle(x, itemWidth / 2, passwordSize / 2, passwordPaint)
        }
    }

    fun updatePassword(number: Int) {
        if (number >= 0 && password.length < 6) {
            password.append(number)
            invalidate()
        } else if (number < 0 && password.isNotEmpty()) {
            password.deleteCharAt(password.length - 1)
            invalidate()
        }
        Log.e("密码", password.toString())

    }

    fun getInputPassword(): String {
        return password.toString()
    }
}