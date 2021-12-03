package com.wuc.music.ui.page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.wuc.music.R
import com.wuc.music.bridge.state.DrawerViewModel
import com.wuc.music.databinding.FragmentDrawerBinding
import com.wuc.music.ui.base.BaseFragment

/**
 * @author : wuchao5
 * @date : 2021/12/3 14:47
 * @desciption : 抽屉的 左侧半界面
 */
class DrawerFragment : BaseFragment() {
  private var mBinding: FragmentDrawerBinding? = null

  private var mDrawerViewModel: DrawerViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mDrawerViewModel = getFragmentViewModelProvider(this).get(DrawerViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_drawer, container, false)
    mBinding = FragmentDrawerBinding.bind(view)
    mBinding?.vm = mDrawerViewModel
    mBinding?.click = ClickProxy()
    return view
  }

  inner class ClickProxy {
    fun logoClick() {
      showShortToast("click")
    }
  }
}