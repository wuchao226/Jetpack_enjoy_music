package com.wuc.music.bridge.data.binding

import android.R
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.wuc.architecture.utils.ClickUtils

/**
 * @author : wuchao5
 * @date : 2021/12/3 17:28
 * @desciption :
 */
object CommonBindingAdapter {

    /**
     * 通过 url 加载 ImageView
     */
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    fun loadUrl(view: ImageView, url: String?, placeholder: Drawable?) {
        Glide.with(view.context).load(url).placeholder(placeholder).into(view)
    }

    /**
     * view 的显示与隐藏
     */
    @BindingAdapter(value = ["visible"], requireAll = false)
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter(value = ["showDrawable", "drawableShowed"], requireAll = false)
    fun showDrawable(view: ImageView, showDrawable: Boolean, drawableShowed: Int) {
        view.setImageResource(if (showDrawable) drawableShowed else R.color.transparent)
    }

    @BindingAdapter(value = ["textColor"], requireAll = false)
    fun setTextColor(textView: TextView, textColorRes: Int) {
        textView.setTextColor(textView.resources.getColor(textColorRes))
    }

    @BindingAdapter(value = ["imageRes"], requireAll = false)
    fun setImageRes(imageView: ImageView, imageRes: Int) {
        imageView.setImageResource(imageRes)
    }

    @BindingAdapter(value = ["selected"], requireAll = false)
    fun selected(view: View, select: Boolean) {
        view.isSelected = select
    }

    /**
     * 消除抖动，防止连续点击
     */
    @JvmStatic
    @BindingAdapter(value = ["onClickWithDebouncing"], requireAll = false)
    fun onClickWithDebouncing(view: View?, clickListener: View.OnClickListener?) {
        ClickUtils.applySingleDebouncing(view, clickListener)
    }
}