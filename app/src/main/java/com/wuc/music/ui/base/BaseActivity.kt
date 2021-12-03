package com.wuc.music.ui.base

import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.wuc.architecture.utils.AdaptScreenUtils
import com.wuc.architecture.utils.BarUtils
import com.wuc.architecture.utils.ScreenUtils
import com.wuc.music.bridge.callback.SharedViewModel
import com.wuc.music.ui.MusicApplication

/**
 * @author : wuchao5
 * @date : 2021/12/2 19:27
 * @desciption : 所有 Activity 的基类
 */
open class BaseActivity : AppCompatActivity() {

  /**
   *  贯穿整个项目的（只会让MusicApplication(Application)初始化一次）
   */
  protected lateinit var mSharedViewModel: SharedViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 给工具类初始化
    BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
    BarUtils.setStatusBarLightMode(this, true)
    mSharedViewModel = getAppViewModelProvider().get<SharedViewModel>(SharedViewModel::class.java)
  }

  /**
   * 用法 ViewModelProvider 【ViewModel共享区域】
   * 此 getAppViewModelProvider 函数，只给 共享的 ViewModel 用（例如：mSharedViewModel .... 等共享的ViewModel）
   */
  protected fun getAppViewModelProvider(): ViewModelProvider {
    return (applicationContext as MusicApplication).getAppViewModelProvider(this)
  }

  /**
   * 此 getActivityViewModelProvider 函数，给所有的 BaseActivity 子类用的 【ViewModel非共享区域】
   */
  protected fun getActivityViewModelProvider(activity: AppCompatActivity): ViewModelProvider {
    return ViewModelProvider(activity, activity.defaultViewModelProviderFactory)
  }

  /**
   * BaseActivity 的 Resource 信息给 暴露给外界用
   */
  override fun getResources(): Resources {
    return if (ScreenUtils.isPortrait) {
      AdaptScreenUtils.adaptWidth(super.getResources(), 360)
    } else {
      AdaptScreenUtils.adaptHeight(super.getResources(), 640)
    }
  }

  fun isDebug(): Boolean {
    return applicationContext.applicationInfo != null &&
        applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
  }

  fun showLongToast(text: String?) {
    Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
  }

  fun showShortToast(text: String?) {
    Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
  }
}