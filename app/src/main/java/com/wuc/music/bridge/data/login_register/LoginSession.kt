package com.wuc.music.bridge.data.login_register


// 保存登录信息的临时会话
data class LoginSession constructor(val isLogin: Boolean, val loginRegisterResponse: LoginRegisterResponse?)