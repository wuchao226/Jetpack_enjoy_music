package com.wuc.music.bridge.request

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wuc.music.bridge.data.login_register.LoginRegisterResponse
import com.wuc.music.bridge.data.repository.HttpRequestManager

/**
 * @author : wuchao5
 * @date : 2021/12/14 15:16
 * @desciption : 请求登录的ViewModel
 */
class RequestLoginViewModel :ViewModel() {
    // 手写 模拟的  by lazy 懒加载功能（使用时 才会真正加载，这才是 懒加载）

    // 注册成功的状态 LiveData
    var registerDataSuccess: MutableLiveData<LoginRegisterResponse>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
        private set

    // 注册失败的状态 LiveData
    var registerDataFail: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
        private set

    fun requestLogin(context: Context, username: String, userpwd: String, reuserpwd: String) {
        // TODO
        // 可以做很多的事情
        // 可以省略很多代码
        // 很多的校验
        // ....

        // 没有任何问题后，直接调用仓库
        HttpRequestManager.instance.login(context, username, userpwd, registerDataSuccess!!, registerDataFail!!)
    }
}