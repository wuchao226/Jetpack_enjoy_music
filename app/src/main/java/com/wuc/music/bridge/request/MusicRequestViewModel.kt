package com.wuc.music.bridge.request

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wuc.music.data.bean.TestAlbum
import com.wuc.music.data.repository.HttpRequestManager

/**
 * @author : wuchao5
 * @date : 2021/12/7 14:14
 * @desciption : 音乐资源 请求 相关的 ViewModel（只负责 MainFragment）
 */
class MusicRequestViewModel : ViewModel() {

    // by lazy 我想手写出 这个效果
    // val age by lazy { 88 }
    var freeMusicesLiveData: MutableLiveData<TestAlbum>? = null
        get() {
            if (field == null) {
                field = MutableLiveData<TestAlbum>()
            }
            return field
        }
        private set

    fun requestFreeMusics() {
        HttpRequestManager.instance.getFreeMusic(freeMusicesLiveData)
    }
}