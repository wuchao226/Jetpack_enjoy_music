package com.wuc.music.bridge.request

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wuc.music.bridge.data.bean.LibraryInfo
import com.wuc.music.bridge.data.repository.HttpRequestManager

/**
 * @author : wuchao5
 * @date : 2021/12/14 20:03
 * @desciption : 抽屉的 左侧半界面 要使用的 ViewModel
 */
class InfoRequestViewModel : ViewModel() {
    var libraryLiveData: MutableLiveData<List<LibraryInfo>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
        private set

    fun requestLibraryInfo() {
        // 请求服务器，协程  RxJava
        // 调用仓库
        HttpRequestManager.instance.getLibraryInfo(libraryLiveData)
    }
}