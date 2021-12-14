package com.wuc.music.bridge.state

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * @author : wuchao5
 * @date : 2021/12/2 18:58
 * @desciption : 首页Fragment 的 MainViewModel
 */
class MainViewModel : ViewModel() {
    // ObservableBoolean  or  LiveData
    // ObservableBoolean 防止抖动，频繁改变，使用这个的场景
    // LiveData 反之

    // MainFragment初始化页面的标记 初始化选项卡和页面  例如：“最近播放”  的记录
    @JvmField
    val initTabAndPage = ObservableBoolean()

    // MainFragment “最佳实践” 里面的 WebView 需要加载的网页链接路径
    @JvmField
    val pageAssetPath = ObservableField<String>()

    // 登录信息的临时数据
    @JvmField
    val loginSessionInfo = ObservableField<String>()
}