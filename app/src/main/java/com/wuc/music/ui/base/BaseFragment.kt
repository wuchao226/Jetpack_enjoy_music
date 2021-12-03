package com.wuc.music.ui.base

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.wuc.music.bridge.callback.SharedViewModel
import com.wuc.music.ui.MusicApplication

/**
 * @author : wuchao5
 * @date : 2021/12/3 14:25
 * @desciption : 所有 Fragment 的父类
 */
open class BaseFragment : Fragment() {
  protected var mActivity: AppCompatActivity? = null
  // private var _sharedViewModel: SharedViewModel
  /**
   *  共享区域的 ViewModel
   */
  protected lateinit var mSharedViewModel: SharedViewModel

  override fun onAttach(context: Context) {
    super.onAttach(context)
    mActivity = context as AppCompatActivity
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mSharedViewModel = getAppViewModelProvider().get<SharedViewModel>(SharedViewModel::class.java)
  }

  /**
   * 给当前 BaseFragmen t用的【共享区域的 ViewModel】
   */
  protected fun getAppViewModelProvider(): ViewModelProvider {
    return (mActivity!!.applicationContext as MusicApplication).getAppViewModelProvider(mActivity!!)
  }

  /**
   * 给所有的子 fragment 提供的函数，可以顺利的拿到 ViewModel 【非共享区域的 ViewModel】
   */
  protected fun getFragmentViewModelProvider(fragment: Fragment): ViewModelProvider {
    return ViewModelProvider(fragment, fragment.defaultViewModelProviderFactory)
  }

  /**
   * 给所有的子 fragment 提供的函数，可以顺利的拿到 ViewModel 【非共享区域的 ViewModel】
   */
  protected fun getActivityViewModelProvider(activity: AppCompatActivity): ViewModelProvider {
    return ViewModelProvider(activity, activity.defaultViewModelProviderFactory)
  }

  /**
   * 为了给所有的 子fragment，导航跳转fragment的
   */
  protected fun nav(): NavController {
    return NavHostFragment.findNavController(this)
  }

  // 测试用的，暂无用
  fun isDebug(): Boolean {
    return mActivity!!.applicationContext.applicationInfo != null &&
        mActivity!!.applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
  }

  fun showLongToast(text: String?) {
    Toast.makeText(mActivity!!.applicationContext, text, Toast.LENGTH_LONG).show()
  }

  fun showShortToast(text: String?) {
    Toast.makeText(mActivity!!.applicationContext, text, Toast.LENGTH_SHORT).show()
  }

  fun showLongToast(stringRes: Int) {
    showLongToast(mActivity!!.applicationContext.getString(stringRes))
  }

  fun showShortToast(stringRes: Int) {
    showShortToast(mActivity!!.applicationContext.getString(stringRes))
  }
}