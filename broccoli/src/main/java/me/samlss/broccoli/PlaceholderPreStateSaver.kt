package me.samlss.broccoli

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Used to restore the state of the original view when the placeholder is cleared.
 */
class PlaceholderPreStateSaver {
    //for TextView.
    /**
     * This is for [android.widget.TextView], when remove the placeholder of view by calling [Broccoli.removePlaceholder]or
     * [Broccoli.clearPlaceholder], will set the text color as [.restoredTextColor] to [android.widget.TextView]
     */
    var restoredTextColor: ColorStateList? = null
    var restoredTextLeftDrawable: Drawable? = null
    var restoredTextTopDrawable: Drawable? = null
    var restoredTextRightDrawable: Drawable? = null
    var restoredTextBottomDrawable: Drawable? = null

    //for ImageView.
    /**
     * This is for [android.widget.ImageView], when remove the placeholder of view by calling [Broccoli.removePlaceholder] or
     * [Broccoli.clearPlaceholder], will set the text color as [.restoredTextColor] to [android.widget.TextView]
     */
    var restoredImageDrawable: Drawable? = null

    //for all views.
    var restoredBackgroundDrawable: Drawable? = null
}
