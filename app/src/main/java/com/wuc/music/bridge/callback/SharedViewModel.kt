package com.wuc.music.bridge.callback

import androidx.lifecycle.ViewModel
import com.wuc.architecture.bridge.callback.UnPeekLiveData

/**
 * @author : wuchao5
 * @date : 2021/12/2 19:30
 * @desciption : SharedViewModel 的职责是用于 页面通信的
 */
class SharedViewModel : ViewModel() {

  // openMenu打开菜单的时候会 set触发---> 改变 openDrawer.setValue(aBoolean); 的值
  val openOrCloseDrawer = UnPeekLiveData<Boolean>()
}