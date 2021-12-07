package com.wuc.music.data.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wuc.architecture.ui.adapter.CommonViewPagerAdapter
import com.wuc.music.R

/**
 * @author : wuchao5
 * @date : 2021/12/3 17:49
 * @desciption : 和fragment_player.xml里面的initTabAndPage挂钩
 */
object TabPageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["initTabAndPage"], requireAll = false)
    fun initTabAndPage(tabLayout: TabLayout, initTabAndPage: Boolean) {
        if (initTabAndPage) {
            val count = tabLayout.tabCount
            val title = arrayOfNulls<String>(count)
            for (i in 0 until count) {
                val tab = tabLayout.getTabAt(i)
                if (tab != null && tab.text != null) {
                    title[i] = tab.text.toString()
                }
            }
            val viewPager: ViewPager = tabLayout.rootView.findViewById(R.id.view_pager)
            if (viewPager != null) {
                viewPager.adapter = CommonViewPagerAdapter(count, false, title)
                tabLayout.setupWithViewPager(viewPager)
            }
        }
    }

    @BindingAdapter(value = ["tabSelectedListener"], requireAll = false)
    fun tabSelectedListener(tabLayout: TabLayout, listener: TabLayout.OnTabSelectedListener?) {
        tabLayout.addOnTabSelectedListener(listener)
    }
}