package me.samlss.broccoli_demo

import android.animation.TimeInterpolator
import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import me.samlss.broccoli.BroccoliGradientDrawable
import me.samlss.broccoli.PlaceholderParameter

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The utils of drawable.
 */

fun createRectangleDrawable(color: Int = Color.parseColor("#dddddd"), cornerRadius: Float = 0f): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    gradientDrawable.cornerRadius = cornerRadius
    gradientDrawable.setColor(color)
    return gradientDrawable
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
fun createRectangleDrawable(colors: IntArray, cornerRadius: Float = 0f): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    gradientDrawable.cornerRadius = cornerRadius
    gradientDrawable.colors = colors
    return gradientDrawable
}

fun createOvalDrawable(color: Int = Color.parseColor("#dddddd")): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.OVAL
    gradientDrawable.setColor(color)
    return gradientDrawable
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
fun createOvalDrawable(colors: IntArray): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.OVAL
    gradientDrawable.colors = colors
    return gradientDrawable
}

/**
 * 创建动画, color 正常颜色, highlightColor 高亮闪烁的颜色
 */
fun createBroccoliGradientDrawable(color: Int = Color.parseColor("#DDDDDD"), highlightColor: Int = Color.parseColor("#CCCCCC"),
                                   radius: Float = 0f, duration: Int = 1000, timeInterpolator: TimeInterpolator = LinearInterpolator()): BroccoliGradientDrawable {
    return BroccoliGradientDrawable(color, highlightColor, radius, duration, timeInterpolator)
}

/**
 * 创建动画
 */
fun createScaleAnimation(isRectangle: Boolean = true,
                         duration: Long = (4..8).random() * 100L,
                         fromX: Float = 0.5f, fromY: Float = 1f): ScaleAnimation {
    return if (isRectangle) {
        val scaleAnimation = ScaleAnimation(fromX, 1f, fromY, 1f)
        scaleAnimation.duration = duration
        scaleAnimation.repeatMode = Animation.REVERSE
        scaleAnimation.repeatCount = Animation.INFINITE
        scaleAnimation
    } else {
        val scaleAnimation = ScaleAnimation(fromX, 1f, fromX, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = duration
        scaleAnimation.repeatMode = Animation.REVERSE
        scaleAnimation.repeatCount = Animation.INFINITE
        scaleAnimation
    }
}

/**
 * 构建参数
 */
fun buildPlaceholderParameter(view: View, drawable: Drawable,
                              showAnimation: Boolean = false,
                              animation: Animation = createScaleAnimation(true)): PlaceholderParameter {
    val builder = PlaceholderParameter.Builder()
    builder.setView(view)
    builder.setDrawable(drawable)
    if (showAnimation) {
        builder.setAnimation(animation)
    }
    return builder.build()
}