package com.wuc.music.bridge.request

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuc.music.bridge.data.login_register.LoginRegisterResponse
import com.wuc.music.bridge.data.repository.HttpRequestManager
import com.wuc.music.ui.view.LoadingDialog
import kotlinx.coroutines.launch

/**
 * @author : wuchao5
 * @date : 2021/12/14 15:16
 * @desciption : 请求登录的ViewModel
 */
class RequestLoginViewModel : ViewModel() {
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

    fun requestLogin(context: Context, username: String, userpwd: String) {
        // TODO
        // 可以做很多的事情
        // 可以省略很多代码
        // 很多的校验
        // ....

        // 没有任何问题后，直接调用仓库
        HttpRequestManager.instance.login(context, username, userpwd, registerDataSuccess!!, registerDataFail!!)
    }

    // 协程函数
    fun requestLoginCoroutine(context: Context, username: String, userpwd: String) {
        // TODO
        // 可以做很多的事情
        // 可以省略很多代码
        // 很多的校验
        // ....

        // GlobalScope(Dispatchers.Main) 全局作用域 默认是异步线程
        // viewModelScope.launch 默认是主线程 == (Dispatchers.Main)
        viewModelScope.launch {
            // 当前是主线程，可以弹框
            LoadingDialog.show(context)

            // 思考：为什么不能这样写？
            // 左边的是： 主线程              右边：异步线程
            // registerData1 ?.value   =    HttpRequestManager.instance.loginCoroutine(username, userpwd)

            // 左边的是： 主线程              右边：异步线程
            val dataResult = HttpRequestManager.instance.loginCoroutine(username, userpwd)

            // 当前是主线程，可以用 setValue更新 状态
            if (dataResult != null) {
                registerDataSuccess?.value = dataResult
            } else {
                registerDataFail?.value = "登录失败，发送了意外，请检查用户名与密码 或者 你的垃圾代码"
            }

            // 当前是主线程，可以 取消弹框
            LoadingDialog.cancel()
        }
    }
}