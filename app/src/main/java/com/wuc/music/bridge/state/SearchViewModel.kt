package com.wuc.music.bridge.state

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * @author : wuchao5
 * @date : 2021/12/2 18:59
 * @desciption : SearchViewModel 是专门为 SearchFragment（搜索界面）服务的
 */
class SearchViewModel : ViewModel() {
    // LiveData  还是选择  DataBinding 的 ObservableField

    // 使用的是 DataBinding 的 ObservableField
    // 1.更新很频繁，因为要把进度更新到拖动条
    // 2.界面 可见 和 不可见，都必须执行，所以不能用 LiveData

    @JvmField // 取出getxx函数
    val progress = ObservableField<Int>()
}