package com.wuc.music.bridge.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author : wuchao5
 * @date : 2021/12/13 20:01
 * @desciption : 管理布局的ViewModel 注册唯一的ViewModel
 */
class RegisterViewModel : ViewModel() {
    @JvmField // @JvmField消除了变量的getter方法
    val userName = MutableLiveData<String>()
    val userPwd = MutableLiveData<String>()
    val registerState = MutableLiveData<String>() // 注册成功，注册失败 等等

    // 默认初始化
    init {
        userName.value = ""
        userPwd.value = ""
        registerState.value = ""
    }
}