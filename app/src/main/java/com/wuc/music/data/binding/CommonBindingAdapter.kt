package com.wuc.music.data.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.wuc.architecture.utils.ClickUtils

/**
 * @author : wuchao5
 * @date : 2021/12/3 17:28
 * @desciption :
 */
object CommonBindingAdapter {

  /**
   * 消除抖动
   */
  @JvmStatic
  @BindingAdapter(value = ["onClickWithDebouncing"], requireAll = false)
  fun onClickWithDebouncing(view: View?, clickListener: View.OnClickListener?) {
    ClickUtils.applySingleDebouncing(view, clickListener)
  }
}