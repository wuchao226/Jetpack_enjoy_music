package com.wuc.music.bridge.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.wuc.music.bridge.data.bean.DownloadFile
import com.wuc.music.bridge.data.bean.LibraryInfo
import com.wuc.music.bridge.data.bean.TestAlbum
import com.wuc.music.bridge.data.login_register.LoginRegisterResponse

/**
 * 远程请求标准接口（在仓库里面）
 * 只为 HttpRequestManager 服务
 */
interface IRemoteRequest {

    fun getFreeMusic(liveData: MutableLiveData<TestAlbum>?)

    fun getLibraryInfo(liveData: MutableLiveData<List<LibraryInfo>>?)

    // 下载文件
    fun downloadFile(liveData: MutableLiveData<DownloadFile>?)

    // 注册
    // 注册的标准
    fun register(
        context: Context,
        username: String,
        password: String,
        repassword: String,
        dataLiveDataSuccess: MutableLiveData<LoginRegisterResponse>,
        dataLiveDataFail: MutableLiveData<String>)

    // 登录
    // 登录的标准
    fun login(
        context: Context,
        username: String,
        password: String,
        dataLiveDataSuccess: MutableLiveData<LoginRegisterResponse>,
        dataLiveDataFail: MutableLiveData<String>)
}