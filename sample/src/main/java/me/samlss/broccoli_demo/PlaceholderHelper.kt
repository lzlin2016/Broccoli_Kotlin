package me.samlss.broccoli_demo

import android.view.View
import me.samlss.broccoli.PlaceholderParameter

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description helper of placeholder
 */
class PlaceholderHelper private constructor() {
    init {
        throw UnsupportedOperationException("Can not be instantiated.")
    }

    companion object {

        fun getParameter(view: View?): PlaceholderParameter? {
            view?.let {
                return when (view.id) {
                    R.id.tv_view_time, R.id.tv_collect_time, R.id.tv_fans, R.id.tv_fans_number,
                    R.id.tv_events, R.id.tv_events_number, R.id.tv_follow, R.id.tv_station ->
                        buildPlaceholderParameter(view, createRectangleDrawable())

                    R.id.tv_price ->
                        buildPlaceholderParameter(view, createRectangleDrawable(cornerRadius = 5f))

                    R.id.iv_clock, R.id.iv_calendar, R.id.iv_location,
                    R.id.iv_arrow_right ->
                        buildPlaceholderParameter(view, createOvalDrawable())

                    R.id.iv_logo ->
                        buildPlaceholderParameter(view, createOvalDrawable(), true, createScaleAnimation(false))

                    R.id.tv_time, R.id.tv_organizer_name, R.id.tv_organizer_description,
                    R.id.tv_location, R.id.tv_location ->
                        buildPlaceholderParameter(view, createRectangleDrawable(cornerRadius = 5f), true)
                    else -> null
                }
            }

            return null
        }
    }
}
