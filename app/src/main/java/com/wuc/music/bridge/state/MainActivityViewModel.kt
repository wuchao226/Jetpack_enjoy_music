package com.wuc.music.bridge.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author : wuchao5
 * @date : 2021/12/2 18:57
 * @desciption : MainActivityViewModel -- activity_main.xml
 */
class MainActivityViewModel : ViewModel() {
  // 首页需要记录抽屉布局的情况 （响应的效果，都让 抽屉控件干了）
  // 打开抽屉 与 关闭抽屉
  @JvmField
  val openDrawer = MutableLiveData<Boolean>()

  // 是否 允许抽屉打开 与 关闭
  @JvmField
  val allowDrawerOpen = MutableLiveData<Boolean>()

  // 构造代码块
  init {
    allowDrawerOpen.value = true
  }
}