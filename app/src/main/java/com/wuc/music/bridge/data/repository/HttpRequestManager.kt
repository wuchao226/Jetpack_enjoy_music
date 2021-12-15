package com.wuc.music.bridge.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wuc.architecture.utils.Utils
import com.wuc.music.R
import com.wuc.music.bridge.data.bean.DownloadFile
import com.wuc.music.bridge.data.bean.LibraryInfo
import com.wuc.music.bridge.data.bean.TestAlbum
import com.wuc.music.bridge.data.login_register.LoginRegisterResponse
import com.wuc.music.bridge.data.repository.api.WanAndroidAPI
import com.wuc.music.bridge.data.repository.net.APIClient
import com.wuc.music.bridge.data.repository.net.APIResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @author : wuchao5
 * @date : 2021/12/7 17:40
 * @desciption : 仓库角色
 */
class HttpRequestManager private constructor() : ILoadRequest, IRemoteRequest {

    // 暂无使用到
    var responseCodeLiveData: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
        private set

    // 仓库：liveData: MutableLiveData<TestAlbum>?  已经和  Request VM 的 LiveData 是同一份了
    override fun getFreeMusic(liveData: MutableLiveData<TestAlbum>?) {
        val gson = Gson()
        val type = object : TypeToken<TestAlbum?>() {}.type
        val testAlbum =
            gson.fromJson<TestAlbum>(Utils.getApp().getString(R.string.free_music_json), type)

        // TODO 在这里可以请求网络
        // TODO 在这里可以请求网络
        // TODO 在这里可以请求数据库
        // .....

        // 子线程  协程  框架  liveData.postValue

        liveData!!.value = testAlbum
    }

    override fun getLibraryInfo(liveData: MutableLiveData<List<LibraryInfo>>?) {
        val gson = Gson()
        val type = object : TypeToken<List<LibraryInfo?>?>() {}.type
        val list =
            gson.fromJson<List<LibraryInfo?>>(Utils.getApp().getString(R.string.library_json), type)
        liveData!!.value = list as List<LibraryInfo>?
    }

    /**
     * 搜索界面
     * TODO：模拟下载任务:
     * 可分别用于 普通的请求，和可跟随页面生命周期叫停的请求，
     * 具体可见 ViewModel 和 UseCase 中的使用。
     *
     * @param liveData 从 Request-ViewModel 或 UseCase 注入 LiveData，用于 控制流程、回传进度、回传文件
     */
    override fun downloadFile(liveData: MutableLiveData<DownloadFile>?) {
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {

                //模拟下载，假设下载一个文件要 10秒、每 100 毫秒下载 1% 并通知 UI 层
                var downloadFile = liveData!!.value
                if (downloadFile == null) {
                    downloadFile = DownloadFile()
                }
                if (downloadFile.progress < 100) {
                    downloadFile.progress = downloadFile.progress + 1
                    Log.d("TAG", "下载进度 " + downloadFile.progress + "%")
                } else {
                    timer.cancel()
                    downloadFile.progress = 0
                    return
                }
                if (downloadFile.isForgive) {
                    timer.cancel()
                    downloadFile.progress = 0
                    return
                }
                liveData.postValue(downloadFile)
                downloadFile(liveData)
            }
        }
        timer.schedule(task, 100)
    }

    override fun register(
        context: Context, username: String, password: String, repassword: String,
        dataLiveDataSuccess: MutableLiveData<LoginRegisterResponse>,
        dataLiveDataFail: MutableLiveData<String>
    ) {
        // RxJava封装网络模型
        APIClient.instance.instanceRetrofit(WanAndroidAPI::class.java)
            .registerAction(username, password, repassword)
            .subscribeOn(Schedulers.io())// 给上面的代码分配异步线程
            .observeOn(AndroidSchedulers.mainThread())// 给下面的代码分配 安卓的主线程
            .subscribe(object : APIResponse<LoginRegisterResponse>(context) {
                override fun success(data: LoginRegisterResponse?) {// RxJava自定义操作符过滤后的
                    dataLiveDataSuccess.value = data
                }

                override fun failure(errorMsg: String?) {// RxJava自定义操作符过滤后的
                    dataLiveDataFail.value = errorMsg
                }

            })
    }

    override fun login(
        context: Context, username: String, password: String,
        dataLiveDataSuccess: MutableLiveData<LoginRegisterResponse>,
        dataLiveDataFail: MutableLiveData<String>
    ) {
        APIClient.instance.instanceRetrofit(WanAndroidAPI::class.java)
            .loginAction(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : APIResponse<LoginRegisterResponse>(context) {
                override fun success(data: LoginRegisterResponse?) {
                    dataLiveDataSuccess.value = data
                }

                override fun failure(errorMsg: String?) {
                    dataLiveDataFail.value = errorMsg
                }
            })
    }

    // 登录的标准-协程版本-的具体代码
    override suspend fun loginCoroutine(
        username: String,
        password: String
    ) =
        APIClient.instance.instanceRetrofit(WanAndroidAPI::class.java)
            .loginActionCoroutine(username, password).data

    companion object {
        val instance = HttpRequestManager()
    }
}