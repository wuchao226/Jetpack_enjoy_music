package com.wuc.music.ui

import android.app.Activity
import android.app.Application
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.wuc.architecture.utils.Utils

/**
 * @author : wuchao5
 * @date : 2021/12/2 17:28
 * @desciption :
 */
class MusicApplication : Application(), ViewModelStoreOwner {
  private var mAppViewModelStore: ViewModelStore? = null
  private var mFactory: ViewModelProvider.Factory? = null
  override fun onCreate() {
    super.onCreate()
    mAppViewModelStore = ViewModelStore()
    Utils.init(this)
  }

  /**
   * 专门给 BaseActivity 与 BaseFragment 用的
   */
  fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
    return ViewModelProvider(
      (activity.applicationContext as MusicApplication),
      (activity.applicationContext as MusicApplication).getAppFactory(activity)
    )
  }


  /**
   * AndroidViewModelFactory 工程是为了创建 ViewModel，给上面的 getAppViewModelProvider 函数用的
   */
  private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
    val checkApplication = checkApplication(activity)
    if (mFactory == null) {
      mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(checkApplication)
    }
    return mFactory!!
  }

  /**
   * 监测下 Activity 是否为null
   */
  private fun checkApplication(activity: Activity): Application {
    return activity.application
      ?: throw IllegalStateException(
        "Your activity/fragment is not yet attached to "
            + "Application. You can't request ViewModel before onCreate call."
      )
  }

  /**
   * 监测下 Fragment 是否为 null
   */
  private fun checkActivity(fragment: Fragment): Activity? {
    return fragment.activity
      ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
  }

  override fun getViewModelStore(): ViewModelStore {
    return mAppViewModelStore!!
  }
}