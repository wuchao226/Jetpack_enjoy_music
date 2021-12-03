package com.wuc.music.ui.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wuc.music.R
import com.wuc.music.bridge.state.MainViewModel
import com.wuc.music.databinding.FragmentMainBinding
import com.wuc.music.ui.base.BaseFragment


class MainFragment : BaseFragment() {

  private var mMainBinding: FragmentMainBinding? = null

  /**
   * 首页 Fragment 的 ViewModel
   */
  private var mMainViewModel: MainViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mMainViewModel = getFragmentViewModelProvider(this).get(MainViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view: View = inflater.inflate(R.layout.fragment_main, container, false)
    mMainBinding = FragmentMainBinding.bind(view)
    // 设置点击事件，布局就可以直接绑定
    mMainBinding?.click = ClickProxy()
    // 设置VM，就可以实时数据变化
    mMainBinding?.vm = mMainViewModel
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // 触发  --->  xxxx
    mMainViewModel?.initTabAndPage?.set(true)

    // 触发，---> 还要加载WebView
    mMainViewModel?.pageAssetPath?.set("JetPack之 WorkManager.html")
  }

  inner class ClickProxy {
    // 当在首页点击 “菜单” 的时候，直接导航到 ---> 菜单的Fragment界面
    fun openMenu() {
      mSharedViewModel.openOrCloseDrawer.value = true
    } // 触发

    // 当在首页点击 “搜索图标” 的时候，直接导航到 ---> 搜索的Fragment界面
    fun search() = nav().navigate(R.id.action_mainFragment_to_searchFragment)
  }
}