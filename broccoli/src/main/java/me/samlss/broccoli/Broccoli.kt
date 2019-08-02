package me.samlss.broccoli

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

import me.samlss.broccoli.util.LogUtil

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description An Android library that shows the placeholder of the view.
 */
class Broccoli {

    private val mPlaceholderInternalImpl: BroccoliInternalImpl = BroccoliInternalImpl()

    /**
     * Add a array of [PlaceholderParameter] to show placeholders for view.
     *
     * @param activity The activity to which the view is attached
     * @param viewIds The view id array.
     */
    fun addPlaceholders(activity: Activity?, vararg viewIds: Int): Broccoli {
        if (activity == null) {
            return this
        }
        addPlaceholders(activity.findViewById<View>(android.R.id.content) as ViewGroup, *viewIds)
        return this
    }

    /**
     * Add a array of [PlaceholderParameter] to show placeholders for view.
     *
     * @param parent The views' parent.
     * @param viewIds The view id array.
     */
    fun addPlaceholders(parent: ViewGroup?, vararg viewIds: Int): Broccoli {
        if (parent == null || viewIds == null) {
            return this
        }

        for (id in viewIds) {
            addPlaceholder(createDefaultParameter(parent.findViewById(id)))
        }

        return this
    }

    /**
     * Add a array of [PlaceholderParameter] to show placeholders for view.
     *
     * @param views The views' that you want to display their placeholder by default.
     */
    fun addPlaceholders(vararg views: View): Broccoli {
        if (views == null) {
            return this
        }

        for (view in views) {
            addPlaceholder(createDefaultParameter(view))
        }

        return this
    }

    /**
     * Get a default [PlaceholderParameter]
     *
     * @param view The view you want to display the placeholder.
     */
    private fun createDefaultParameter(view: View): PlaceholderParameter {
        return PlaceholderParameter.Builder()
                .setView(view)
                .setColor(DEFAULT_PLACEHOLDER_COLOR)
                .build()
    }

    /**
     * Add a [PlaceholderParameter] to show placeholder for view.
     *
     * @param parameter The parameter of the placeholder.
     */
    fun addPlaceholder(parameter: PlaceholderParameter?): Broccoli {
        if (parameter?.view == null) {
            LogUtil.logE("If you want to display a placeholder for view, you can't pass a null parameter or view")
            return this
        }
        mPlaceholderInternalImpl.addPlaceholder(parameter)
        return this
    }

    /**
     * Add a list of [PlaceholderParameter] to show placeholders for view.
     *
     * @param placeholderParameters The placeholderParameter list.
     */
    fun addPlaceholder(placeholderParameters: List<PlaceholderParameter>?): Broccoli {
        if (placeholderParameters == null || placeholderParameters.isEmpty()) {
            return this
        }

        for (parameter in placeholderParameters) {
            addPlaceholder(parameter)
        }
        return this
    }

    /**
     * Add a array of [PlaceholderParameter] to show placeholders for view.
     *
     * @param placeholderParameters The placeholderParameter array.
     */
    fun addPlaceholders(vararg placeholderParameters: PlaceholderParameter): Broccoli {
        if (placeholderParameters == null || placeholderParameters.isEmpty()) {
            return this
        }

        for (parameter in placeholderParameters) {
            addPlaceholder(parameter)
        }
        return this
    }

    /**
     * Remove the placeholder of view.
     * Will remove the record of the view from [Broccoli].
     * Will restore the state of the view itself, such as view's background, TextView's textColor, ImageView's imageDrawable, etc.
     * @param view The placeholder of the view which needs to be removed.
     */
    fun removePlaceholder(view: View): Broccoli {
        mPlaceholderInternalImpl.removePlaceholder(view)
        return this
    }

    /**
     * Will clear the placeholder of view. But unlike [.removePlaceholder],
     * clear placeholder just clear the view's placeholder, but will not remove the record of the view from [Broccoli].
     * Will restore the state of the view itself, such as view's background, TextView's textColor, ImageView's imageDrawable, etc.
     * @param view The placeholder of the view which needs to be cleared.
     */
    fun clearPlaceholder(view: View): Broccoli {
        mPlaceholderInternalImpl.clearPlaceholder(view)
        return this
    }

    /**
     * Clear & remove all the placeholders for the view you have added.
     * Will restore the state of the view itself, such as view's background, TextView's textColor, ImageView's imageDrawable, etc.
     * If using [BroccoliGradientDrawable], will cancel the gradient animation.
     */
    fun removeAllPlaceholders() {
        mPlaceholderInternalImpl.hide(true)
    }

    /**
     * Clear all the placeholders for the view you have added.
     * Will restore the state of the view itself, such as view's background, TextView's textColor, ImageView's imageDrawable, etc.
     * If using [BroccoliGradientDrawable], will cancel the gradient animation.
     */
    fun clearAllPlaceholders() {
        mPlaceholderInternalImpl.hide(false)
    }

    /**
     * Start to show all placeholders for the view you have set.
     */
    fun show() {
        mPlaceholderInternalImpl.show()
    }

    companion object {
         val DEFAULT_PLACEHOLDER_COLOR = Color.parseColor("#dddddd")
    }
}
