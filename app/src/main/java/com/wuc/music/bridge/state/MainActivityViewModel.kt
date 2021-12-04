package com.wuc.music.bridge.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author : wuchao5
 * @date : 2021/12/2 18:57
 * @desciption : MainActivityViewModel -- activity_main.xml
 */
class MainActivityViewModel : ViewModel() {

    /*
   @JvmField消除了变量的getter与setter方法
   @JvmField修饰的变量不能是private属性的
   @JvmStatic只能在object类或者伴生对象companion object中使用，而@JvmField没有这些限制
   @JvmStatic一般用于修饰方法，使方法变成真正的静态方法；如果修饰变量不会消除变量的getter与setter方法，但会使getter与setter方法和变量都变成静态
   */
    // 首页需要记录抽屉布局的情况 （响应的效果，都让 抽屉控件干了）
    // 打开抽屉 与 关闭抽屉
    @JvmField // @JvmField消除了变量的getter方法
    val openDrawer = MutableLiveData<Boolean>()

    // 是否 允许抽屉打开 与 关闭
    @JvmField
    val allowDrawerOpen = MutableLiveData<Boolean>()

    // 构造代码块
    init {
        allowDrawerOpen.value = true
    }
}