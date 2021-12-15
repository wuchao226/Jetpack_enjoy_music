package com.wuc.music.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wuc.music.R
import com.wuc.music.bridge.data.login_register.LoginRegisterResponse
import com.wuc.music.bridge.data.login_register.LoginSession
import com.wuc.music.bridge.request.RequestLoginViewModel
import com.wuc.music.bridge.state.LoginViewModel
import com.wuc.music.databinding.ActivityUserLoginBinding
import com.wuc.music.ui.base.BaseActivity

class LoginActivity : BaseActivity() {
    private var mLoginBinding: ActivityUserLoginBinding? = null
    private var mLoginViewModel: LoginViewModel? = null
    private var mRequestLoginViewModel: RequestLoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()
        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_login)
        mLoginViewModel = getActivityViewModelProvider(this).get(LoginViewModel::class.java)
        mRequestLoginViewModel = getActivityViewModelProvider(this).get(RequestLoginViewModel::class.java)

        mLoginBinding?.lifecycleOwner = this
        mLoginBinding?.vm = mLoginViewModel
        mLoginBinding?.click = ClickClass()

        // 登录成功 眼睛监听 成功
        mRequestLoginViewModel?.registerDataSuccess?.observe(this, {
            loginSuccess(it!!)
        })

        // 登录失败 眼睛监听 失败
        mRequestLoginViewModel?.registerDataFail?.observe(this, {
            loginFialure(it!!)
        })
    }

    // 响应的两个函数
    private fun loginSuccess(registerBean: LoginRegisterResponse?) {
        //  Toast.makeText(this@LoginActivity, "登录成功😀", Toast.LENGTH_SHORT).show()
        mLoginViewModel?.loginState?.value = "恭喜 ${registerBean?.username} 用户，登录成功"

        // 登录成功 在跳转首页之前，需要 保存 登录的会话
        // 保存登录的临时会话信息
        mSharedViewModel.session.value = LoginSession(true, registerBean)

        // 跳转到首页
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    private fun loginFialure(errorMsg: String?) {
        // Toast.makeText(this@LoginActivity, "登录失败 ~ 呜呜呜", Toast.LENGTH_SHORT).show()
        mLoginViewModel?.loginState?.value = "骚瑞 登录失败，错误信息是:${errorMsg}"
    }

    inner class ClickClass {

        // 点击事件，登录的函数
        fun loginAction() {
            if (mLoginViewModel!!.userName.value.isNullOrBlank() || mLoginViewModel!!.userPwd.value.isNullOrBlank()) {
                mLoginViewModel?.loginState?.value = "用户名 或 密码 为空，请你好好检查"
                return
            }
            // 非协程版本
            /*mRequestLoginViewModel?.requestLogin(
                this@LoginActivity,
                mLoginViewModel!!.userName.value!!,
                mLoginViewModel!!.userPwd.value!!
            )*/
            mRequestLoginViewModel?.requestLoginCoroutine(
                this@LoginActivity, mLoginViewModel!!.userName.value!!,
                mLoginViewModel!!.userPwd.value!!,
            )
        }

        // 跳转到 注册界面
        fun startToRegister() = startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }
}