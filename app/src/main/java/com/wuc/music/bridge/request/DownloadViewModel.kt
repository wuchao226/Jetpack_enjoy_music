package com.wuc.music.bridge.request

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wuc.music.bridge.data.bean.DownloadFile
import com.wuc.music.bridge.data.repository.HttpRequestManager

/**
 * @author : wuchao5
 * @date : 2021/12/14 19:03
 * @desciption :
 */
class DownloadViewModel : ViewModel() {
    var downloadFileLiveData: MutableLiveData<DownloadFile>? = null
        get() {
            if (field == null) {
                field = MutableLiveData<DownloadFile>()
            }
            return field
        }
        private set

    var downloadFileCanBeStoppedLiveData: MutableLiveData<DownloadFile>? = null
        get() {
            if (field == null) {
                field = MutableLiveData<DownloadFile>()
            }
            return field
        }
        private set

    // 请求仓库 模拟下载功能
    fun requestDownloadFile() = HttpRequestManager.instance.downloadFile(downloadFileLiveData)
}