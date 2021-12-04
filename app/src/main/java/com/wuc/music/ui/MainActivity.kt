package com.wuc.music.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.wuc.music.R
import com.wuc.music.bridge.state.MainActivityViewModel
import com.wuc.music.bridge.state.MainViewModel
import com.wuc.music.databinding.ActivityMainBinding
import com.wuc.music.ui.base.BaseActivity

/**
 * @author : wuchao5
 * @date : 2021/12/3 15:28
 * @desciption : 主页  管理者
 */
class MainActivity : BaseActivity() {

  private var mMainActivityBinding: ActivityMainBinding? = null
  private var mMainActivityViewModel: MainActivityViewModel? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mMainActivityViewModel = getActivityViewModelProvider(this).get(MainActivityViewModel::class.java)
    mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    mMainActivityBinding ?.lifecycleOwner = this
    mMainActivityBinding?.vm = mMainActivityViewModel

    // 间接的可以打开菜单 （观察）
    mSharedViewModel.openOrCloseDrawer.observe(this, {
      // 触发，就会改变，---> 观察（打开菜单逻辑）
      mMainActivityViewModel?.openDrawer?.value = it
    })

    // mMainActivityViewModel 的 变化 先暂停  (抽屉控件干了，我就不需要干了)
   /* mMainActivityViewModel?.openDrawer?.observe(this, {

    })*/

    // 共享 （观察）
    mSharedViewModel.activityCanBeClosedDirectly.observe(this, {
      // 先不写，作用不大
    })
  }
}