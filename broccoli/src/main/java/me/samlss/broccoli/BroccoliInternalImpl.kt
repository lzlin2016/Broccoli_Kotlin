package me.samlss.broccoli

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import java.util.Collections
import java.util.HashMap

import me.samlss.broccoli.util.Utils

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description  Mainly responsible for the show the all the placeholder of views.
 */
internal class BroccoliInternalImpl {
    private var mPlaceholderViewMap: MutableMap<View, PlaceholderParameter>? = null

    init {
        synchronizedMap()
    }

    private fun synchronizedMap() {
        mPlaceholderViewMap = Collections.synchronizedMap(HashMap())
    }

    /**
     * Add a placeholder for view.
     *
     * @param parameter The parameter of the placeholder.
     */
    fun addPlaceholder(parameter: PlaceholderParameter) {
        if (mPlaceholderViewMap == null) {
            synchronizedMap()
        }

        mPlaceholderViewMap!![parameter.view!!] = parameter
    }

    /**
     * Remove the placeholder of view.
     *
     * @param view The placeholder of the view which needs to be removed.
     */
    fun removePlaceholder(view: View) {
        if (mPlaceholderViewMap == null) {
            return
        }

        if (mPlaceholderViewMap!!.containsKey(view)) {
            clearPlaceholder(view)
            mPlaceholderViewMap!!.remove(view)
        }
    }

    fun clearPlaceholder(view: View?) {
        val parameter = mPlaceholderViewMap!![view]
        if (view == null || parameter == null) {
            return
        }

        view.clearAnimation()

        if (view.background != null && view.background is BroccoliGradientDrawable) {
            (view.background as BroccoliGradientDrawable).cancelAnimation()
        }

        if (view is TextView) {
            restoreTextViewState(view as TextView?, parameter.placeholderPreStateSaver)
        } else if (view is ImageView) {
            restoreImageState(view as ImageView?, parameter.placeholderPreStateSaver)
        }

        restoreBackgroundState(view, parameter.placeholderPreStateSaver)
    }

    private fun restoreTextViewState(textView: TextView?, stateSaver: PlaceholderPreStateSaver?) {
        if (textView == null || stateSaver == null) {
            return
        }

        textView.setTextColor(stateSaver.restoredTextColor)
        textView.setCompoundDrawables(stateSaver.restoredTextLeftDrawable,
                stateSaver.restoredTextTopDrawable,
                stateSaver.restoredTextRightDrawable,
                stateSaver.restoredTextBottomDrawable)
    }

    private fun restoreImageState(imageView: ImageView?, stateSaver: PlaceholderPreStateSaver?) {
        if (imageView == null || stateSaver == null) {
            return
        }

        imageView.setImageDrawable(stateSaver.restoredImageDrawable)
        Utils.setBackgroundDrawable(imageView, stateSaver.restoredBackgroundDrawable)
    }

    private fun restoreBackgroundState(view: View?, stateSaver: PlaceholderPreStateSaver?) {
        if (view == null || stateSaver == null) {
            return
        }

        Utils.setBackgroundDrawable(view, stateSaver.restoredBackgroundDrawable)
    }

    /**
     * Start to show all placeholders for the view you have set.
     */
    fun show() {
        if (mPlaceholderViewMap == null || mPlaceholderViewMap!!.isEmpty()) {
            return
        }

        for (parameter in mPlaceholderViewMap!!.values) {
            showPlaceholder(parameter)
            showPlaceholderAnimation(parameter)
            showPlaceholderGradientDrawableAnimation(parameter)
        }
    }

    private fun showPlaceholderGradientDrawableAnimation(parameter: PlaceholderParameter?) {
        if ((parameter?.view == null
                || parameter.view!!.background == null) || !(parameter.view!!.background is BroccoliGradientDrawable)) {
            return
        }

        (parameter.view!!.background as BroccoliGradientDrawable).attachedView(parameter.view!!)
    }

    fun hide(removed: Boolean) {
        if (mPlaceholderViewMap == null || mPlaceholderViewMap!!.isEmpty()) {
            return
        }
        for (view in mPlaceholderViewMap!!.keys) {
            clearPlaceholder(view)
        }

        if (removed) {
            mPlaceholderViewMap!!.clear()
        }
    }

    private fun getOrNewPreStateSaver(parameter: PlaceholderParameter): PlaceholderPreStateSaver {
        var placeholderPreStateSaver = parameter.placeholderPreStateSaver
        if (placeholderPreStateSaver == null) {
            placeholderPreStateSaver = PlaceholderPreStateSaver()
            parameter.placeholderPreStateSaver = placeholderPreStateSaver
        }

        return placeholderPreStateSaver
    }

    private fun recordTextViewOriginalState(textView: TextView?, parameter: PlaceholderParameter?) {
        if (textView == null || parameter == null)
            return

        val placeholderPreStateSaver = getOrNewPreStateSaver(parameter)

        val drawables = textView.compoundDrawables
        placeholderPreStateSaver.restoredTextLeftDrawable = drawables[0]
        placeholderPreStateSaver.restoredTextTopDrawable = drawables[1]
        placeholderPreStateSaver.restoredTextRightDrawable = drawables[2]
        placeholderPreStateSaver.restoredTextBottomDrawable = drawables[3]

        placeholderPreStateSaver.restoredTextColor = textView.textColors
        placeholderPreStateSaver.restoredBackgroundDrawable = textView.background

        textView.setCompoundDrawables(null, null, null, null)
        textView.setTextColor(Color.TRANSPARENT)
    }

    private fun recordImageViewOriginalState(imageView: ImageView?, parameter: PlaceholderParameter?) {
        if (imageView == null || parameter == null)
            return

        val placeholderPreStateSaver = getOrNewPreStateSaver(parameter)
        placeholderPreStateSaver.restoredImageDrawable = imageView.drawable

        imageView.setImageDrawable(null)
        Utils.setBackgroundDrawable(imageView, null)
    }

    private fun recordBackgroundState(view: View?, parameter: PlaceholderParameter?) {
        if (view == null || parameter == null)
            return

        val placeholderPreStateSaver = getOrNewPreStateSaver(parameter)
        placeholderPreStateSaver.restoredBackgroundDrawable = view.background
    }

    private fun showPlaceholder(parameter: PlaceholderParameter) {
        if (parameter.view == null || parameter == null) {
            return
        }
        val view = parameter.view


        if (view is ImageView) {
            recordImageViewOriginalState(view as ImageView?, parameter)
        } else if (view is TextView) {
            recordTextViewOriginalState(view as TextView?, parameter)
        }

        recordBackgroundState(view, parameter)

        if (parameter.drawable != null) {
            Utils.setBackgroundDrawable(view, parameter.drawable)
            return
        }

        if (parameter.drawableRes != 0) {
            Utils.setBackgroundDrawable(view, Utils.getDrawable(view, parameter.drawableRes))
            return
        }

        if (parameter.color != 0) {
            view!!.setBackgroundColor(parameter.color)
            return
        }

        if (parameter.colorRes != 0) {
            view!!.setBackgroundColor(Utils.getColor(view, parameter.colorRes))
            return
        }

        view!!.setBackgroundColor(Broccoli.DEFAULT_PLACEHOLDER_COLOR)
    }

    private fun showPlaceholderAnimation(parameter: PlaceholderParameter?) {
        if (parameter?.view == null
                || parameter.animation == null) {
            return
        }

        parameter.view!!.startAnimation(parameter.animation)
    }
}
