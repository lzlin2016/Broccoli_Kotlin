package me.samlss.broccoli_demo

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import me.samlss.broccoli.Broccoli

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description ding ding check work sample.
 */
class DingDingSampleActivity : AppCompatActivity() {
    private var mBroccoli: Broccoli? = null
    private val mHandler = Handler()

    private val task = Runnable { mBroccoli!!.removeAllPlaceholders() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dingding)

        initPlaceholders()
    }

    private fun initPlaceholders() {
        mBroccoli = Broccoli()
        mBroccoli!!.addPlaceholders(
                buildPlaceholderParameter(findViewById(R.id.iv_head), createOvalDrawable()),
                buildPlaceholderParameter(findViewById(R.id.tv_name), createRectangleDrawable()),
                buildPlaceholderParameter(findViewById(R.id.tv_description), createRectangleDrawable()),
                buildPlaceholderParameter(findViewById(R.id.tv_day_des), createRectangleDrawable()),
                buildPlaceholderParameter(findViewById(R.id.v_shangban), createOvalDrawable()),
                buildPlaceholderParameter(findViewById(R.id.v_xiaban), createOvalDrawable()),
                buildPlaceholderParameter(findViewById(R.id.v_timeline), createRectangleDrawable()),
                buildPlaceholderParameter(findViewById(R.id.tv_shangban), createRectangleDrawable()),
                buildPlaceholderParameter(findViewById(R.id.tv_xiaban), createRectangleDrawable()),
                buildPlaceholderParameter(findViewById(R.id.tv_daka), createOvalDrawable()),
                buildPlaceholderParameter(findViewById(R.id.tv_daka_des), createRectangleDrawable())
        )

        showPlaceholders()
    }

    private fun showPlaceholders() {
        mBroccoli!!.show()
        mHandler.removeCallbacks(task)
        mHandler.postDelayed(task, 2000)
    }
}
