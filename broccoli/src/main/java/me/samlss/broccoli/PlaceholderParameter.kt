package me.samlss.broccoli

import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.Animation

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The parameter of placeholder
 */
class PlaceholderParameter private constructor() {
    var color: Int = 0
    var colorRes: Int = 0
    var drawable: Drawable? = null
    var drawableRes: Int = 0

    var animation: Animation? = null
    var view: View? = null
    var placeholderPreStateSaver: PlaceholderPreStateSaver? = null

    class Builder {
        private val mPlaceholderParameter: PlaceholderParameter

        init {
            mPlaceholderParameter = PlaceholderParameter()
        }

        /**
         * Set the view which you want to display placeholder.
         */
        fun setView(view: View): Builder {
            mPlaceholderParameter.view = view
            return this
        }

        /**
         * Set the color of the placeholder.
         *
         * @param color The color value.
         */
        fun setColor(color: Int): Builder {
            mPlaceholderParameter.color = color
            return this
        }

        /**
         * Set the color of the placeholder.
         *
         * @param colorRes The color resource id.
         */
        fun setColorRes(colorRes: Int): Builder {
            mPlaceholderParameter.colorRes = colorRes
            return this
        }

        /**
         * Set the drawable of the placeholder.
         *
         * @param drawable The drawable.
         */
        fun setDrawable(drawable: Drawable): Builder {
            mPlaceholderParameter.drawable = drawable
            return this
        }

        /**
         * Set the drawable of the placeholder.
         *
         * @param drawableRes The drawable resource id.
         */
        fun setDrawableRes(drawableRes: Int): Builder {
            mPlaceholderParameter.drawableRes = drawableRes
            return this
        }

        fun setAnimation(animation: Animation): Builder {
            mPlaceholderParameter.animation = animation
            return this
        }

        /**
         * Now build a [PlaceholderParameter]
         */
        fun build(): PlaceholderParameter {
            return mPlaceholderParameter
        }
    }
}
