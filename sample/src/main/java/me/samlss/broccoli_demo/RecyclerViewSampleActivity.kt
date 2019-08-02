package me.samlss.broccoli_demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.samlss.broccoli.Broccoli
import java.util.*

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Sample of RecyclerView.
 */
class RecyclerViewSampleActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var myAdapter: MyAdapter? = null
    private val mDataList = ArrayList<DataBean>()


    private val mViewPlaceholderManager = HashMap<View, Broccoli>()
    private val mTaskManager = HashMap<View, Runnable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_sample)

        initData()
        initRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataList.clear()

        for (view in mTaskManager.keys) {
            view.removeCallbacks(mTaskManager[view])
        }

        //Prevent memory leaks when using BroccoliGradientDrawable
        //防止使用BroccoliGradientDrawable时内存泄露
        for (broccoli in mViewPlaceholderManager.values) {
            broccoli.removeAllPlaceholders()
        }

        mViewPlaceholderManager.clear()
        mTaskManager.clear()
    }

    private fun initData() {
        for (i in 0..19) {
            val dataBean = DataBean()
            dataBean.imageRes = sImageIds[i % sImageIds.size]
            dataBean.title = sTitles[i % sTitles.size]
            dataBean.description = sDescriptions[i % sDescriptions.size]
            dataBean.price = sPrices[i % sPrices.size]

            mDataList.add(dataBean)
        }
    }

    private fun initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview)
        mRecyclerView!!.layoutManager = LinearLayoutManager(applicationContext)

        myAdapter = MyAdapter()
        mRecyclerView!!.adapter = myAdapter
    }


    private inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
            val view = layoutInflater.inflate(R.layout.recyclerview_sample_item, viewGroup, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
            var broccoli = mViewPlaceholderManager[myViewHolder.itemView]
            if (broccoli == null) {
                broccoli = Broccoli()
                mViewPlaceholderManager[myViewHolder.itemView] = broccoli
            }

            broccoli.removeAllPlaceholders()
            broccoli.addPlaceholder(buildPlaceholderParameter(myViewHolder.tvTitle, createBroccoliGradientDrawable()))
            broccoli.addPlaceholder(buildPlaceholderParameter(myViewHolder.imageView, createBroccoliGradientDrawable()))
            broccoli.addPlaceholder(buildPlaceholderParameter(myViewHolder.tvPrice, createBroccoliGradientDrawable()))
            broccoli.addPlaceholder(buildPlaceholderParameter(myViewHolder.tvDescription, createBroccoliGradientDrawable()))
            broccoli.show()


            //delay to show the data
            var task = mTaskManager[myViewHolder.itemView]
            if (task == null) {
                val finalBroccoli = broccoli
                task = Runnable {
                    //when you need to update data, you must to call the remove or the clear method.
                    finalBroccoli.removeAllPlaceholders()

                    if (mDataList.isEmpty()) {
                        return@Runnable
                    }

                    myViewHolder.imageView.setImageResource(mDataList[i].imageRes)
                    myViewHolder.tvPrice.text = "¥ " + mDataList[i].price.toString()
                    myViewHolder.tvTitle.text = mDataList[i].title
                    myViewHolder.tvDescription.text = mDataList[i].description
                }
                mTaskManager[myViewHolder.itemView] = task
            } else {
                myViewHolder.itemView.removeCallbacks(task)
            }
            myViewHolder.itemView.postDelayed(task, 3000)
        }

        override fun getItemCount(): Int {
            return mDataList.size
        }
    }

    private inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)

    }


    private class DataBean {
        internal var imageRes: Int = 0
        var title: String? = null
        var description: String? = null
        var price: Int = 0
    }

    companion object {
        private val sImageIds = intArrayOf(R.mipmap.photo_1, R.mipmap.photo_2, R.mipmap.photo_3, R.mipmap.photo_4, R.mipmap.photo_5)

        private val sPrices = intArrayOf(549, 1499, 1199, 1699, 3388)

        private val sTitles = arrayOf("honor/荣耀 畅玩7", "Huawei/华为 畅想MAX", "honor/荣耀 荣耀9i", "Huawei/华为 畅想9 PLUS", "Huawei/华为 P20")

        private val sDescriptions = arrayOf("2018.05上市 | 免举证退换货", "2018.10上市 | 免举证退换货", "2018.06上市 | 免举证退换货", "2018.10上市 | 免举证退换货", "2018.04上市 | 免举证退换货")
    }
}
