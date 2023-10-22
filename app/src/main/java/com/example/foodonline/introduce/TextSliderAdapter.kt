package com.example.foodonline.introduce

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.foodonline.R
import kotlin.math.min

class TextSliderAdapter(private val mContext: Context, private val mListText1: List<String>, private val mListText2: List<String>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.fragment_onboard, container, false)

        val textView1 = view.findViewById<TextView>(R.id.textView)
        val textView2 = view.findViewById<TextView>(R.id.textShadow)

        if (position >= 0 && position < mListText1.size) {
            val text1 = mListText1[position]
            textView1.text = text1
        }

        if (position >= 0 && position < mListText2.size) {
            val text2 = mListText2[position]
            textView2.text = text2
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        // Đảm bảo rằng cả hai danh sách có cùng độ dài
        return min(mListText1.size, mListText2.size)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}
