package me.samlss.broccoli

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import android.util.Log
import android.view.View

import java.lang.ref.WeakReference

import me.samlss.broccoli.util.LogUtil

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The gradient animation drawable class for this library
 */
class BroccoliGradientDrawable : ShapeDrawable {
    private var mColors: IntArray? = null
    private var mNormalColor: Int = 0
    private var mHighlightColor: Int = 0
    private var mCanvasWidth: Int = 0
    private var mCanvasHeight: Int = 0

    private var valueAnimator: ValueAnimator? = null
    private var mDuration: Int = 0
    private var mTimeInterpolator: TimeInterpolator? = null
    private var mAnimatedValue: Int = 0

    /**
     * @param xStartCoordinate The x-coordinate for the start of the gradient line
     * @param xEndCoordinate The y-coordinate for the start of the gradient line
     */
    private var xStartCoordinate: Float = 0.toFloat()
    private var xEndCoordinate: Float = 0.toFloat()
    private var mGradientCanvas: Canvas? = null
    private var mGradientLayer: Bitmap? = null

    private var mBackgroundCanvas: Canvas? = null
    private var mBackgroundLayer: Bitmap? = null

    private var mViewRef: WeakReference<View>? = null

    private val ovalDrawable: OvalShape
        get() = OvalShape()

    /**
     * Construct a rectangle drawable.
     *
     * @param color The color used to fill the shape.
     * @param highlightColor The color of gradient animation.
     * @param radius The radius in pixels of the corners of the rectangle shape.
     * @param duration The duration of the gradient animation.
     * @param timeInterpolator The interpolator of the gradient animation.
     */
    constructor(color: Int, highlightColor: Int, radius: Float, duration: Int,
                timeInterpolator: TimeInterpolator) {
        shape = getReboundRect(radius)
        init(color, highlightColor, duration, timeInterpolator)
    }

    /**
     * Construct an oval drawable.
     *
     * @param color The color used to fill the shape.
     * @param highlightColor The color of gradient animation.
     * @param duration The duration of the gradient animation.
     * @param timeInterpolator The interpolator of the gradient animation.
     */
    constructor(color: Int, highlightColor: Int, duration: Int,
                timeInterpolator: TimeInterpolator) {
        shape = ovalDrawable
        init(color, highlightColor, duration, timeInterpolator)
    }

    private fun init(color: Int, highlightColor: Int, duration: Int,
                     timeInterpolator: TimeInterpolator) {
        mDuration = duration
        mTimeInterpolator = timeInterpolator
        mNormalColor = color
        mHighlightColor = highlightColor

        mColors = intArrayOf(mNormalColor, mHighlightColor, mNormalColor)
    }

    private fun getReboundRect(radius: Float): RoundRectShape {
        return RoundRectShape(floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius), null, null)
    }

    private fun setupAnimator() {
        cancelAnimation()
        if (mCanvasWidth == 0 || mCanvasHeight == 0) {
            LogUtil.logE("width and height must be > 0")
            return
        }

        mGradientLayer = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight, Bitmap.Config.ALPHA_8)
        mGradientCanvas = Canvas(mGradientLayer!!)

        mBackgroundLayer = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight, Bitmap.Config.ARGB_8888)
        mBackgroundCanvas = Canvas(mBackgroundLayer!!)

        xStartCoordinate = (-mCanvasWidth).toFloat()
        valueAnimator = ValueAnimator.ofInt(-mCanvasWidth, mCanvasWidth)
        valueAnimator!!.duration = mDuration.toLong()
        valueAnimator!!.interpolator = mTimeInterpolator
        valueAnimator!!.repeatMode = ValueAnimator.RESTART
        valueAnimator!!.repeatCount = ValueAnimator.INFINITE

        valueAnimator!!.addUpdateListener { animation ->
            mAnimatedValue = animation.animatedValue as Int
            invalidateSelf()
        }
        valueAnimator!!.start()
    }

    override fun draw(canvas: Canvas) {
        if (canvas.width <= 0
                || canvas.height <= 0
                || shape == null) {
            super.draw(canvas)
            return
        }

        if (mViewRef!!.get() == null || mViewRef!!.get()?.background !== this) {
            cancelAnimation()
            return
        }

        if (valueAnimator == null) {
            mCanvasWidth = canvas.width
            mCanvasHeight = canvas.height

            setupAnimator()
        }

        paint.color = mNormalColor
        shape.draw(mBackgroundCanvas, paint)
        canvas.drawBitmap(mBackgroundLayer!!, 0f, 0f, paint)

        xStartCoordinate = mAnimatedValue.toFloat()
        xEndCoordinate = xStartCoordinate + mCanvasWidth
        paint.shader = LinearGradient(xStartCoordinate, 0f, xEndCoordinate, 0f, mColors!!, floatArrayOf(0f, 0.4f, 0.8f), Shader.TileMode.CLAMP)
        shape.draw(mGradientCanvas, paint)
        canvas.drawBitmap(mGradientLayer!!, 0f, 0f, paint)
    }

    /**
     * Attach the view to this drawable.
     *
     * @param view The view which use this drawable.
     */
    public fun attachedView(view: View) {
        mViewRef = WeakReference(view)
        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {

            }

            override fun onViewDetachedFromWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                cancelAnimation()
            }
        })
    }

    /**
     * Cancel the gradient animation.
     */
    fun cancelAnimation() {
        if (valueAnimator != null) {
            valueAnimator!!.cancel()
            valueAnimator = null
        }

        if (mGradientLayer != null) {
            if (!mGradientLayer!!.isRecycled) {
                mGradientLayer!!.recycle()
            }

            mGradientLayer = null
        }

        if (mBackgroundLayer != null) {
            if (!mBackgroundLayer!!.isRecycled) {
                mBackgroundLayer!!.recycle()
            }

            mGradientLayer = null
        }
    }
}
