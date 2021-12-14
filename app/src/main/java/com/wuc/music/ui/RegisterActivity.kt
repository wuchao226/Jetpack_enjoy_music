package com.wuc.music.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wuc.music.R
import com.wuc.music.bridge.data.login_register.LoginRegisterResponse
import com.wuc.music.bridge.request.RequestRegisterViewModel
import com.wuc.music.bridge.state.RegisterViewModel
import com.wuc.music.databinding.ActivityUserRegisterBinding
import com.wuc.music.ui.base.BaseActivity

/**
 * @author : wuchao5
 * @date : 2021/12/13 19:59
 * @desciption : 注册功能的Activity
 */
class RegisterActivity : BaseActivity() {

    var mMainBinding: ActivityUserRegisterBinding? = null // 当前Register的布局
    var mRegisterViewModel: RegisterViewModel? = null // ViewModel

    var mRequestRegisterViewModel: RequestRegisterViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()

        mRegisterViewModel = getActivityViewModelProvider(this).get(RegisterViewModel::class.java) // 状态VM
        mRequestRegisterViewModel = getActivityViewModelProvider(this).get(RequestRegisterViewModel::class.java) // 请求VM
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_register) // 初始化DB
        mMainBinding?.lifecycleOwner = this
        mMainBinding?.vm = mRegisterViewModel // DataBinding绑定 ViewModel
        mMainBinding?.click = ClickClass() // 布局建立点击事件

        // 一双眼睛 盯着 requestRegisterViewModel 监听 成功了吗
        mRequestRegisterViewModel?.registerDataSuccess?.observe(this, {
            registerSuccess(it)
        })
        // 一双眼睛 盯着 requestRegisterViewModel 监听 失败了吗
        mRequestRegisterViewModel?.registerDataFail?.observe(this, {
            registerFailed(it)
        })
    }

    private fun registerSuccess(registerBean: LoginRegisterResponse?) {
        // Toast.makeText(this, "注册成功😀", Toast.LENGTH_SHORT).show()
        mRegisterViewModel?.registerState?.value = "恭喜 ${registerBean?.username} 用户，注册成功"

        // TODO 注册成功，直接进入的登录界面  同学们去写
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun registerFailed(errorMsg: String?) {
        // Toast.makeText(this, "注册失败 ~ 呜呜呜", Toast.LENGTH_SHORT).show()
        mRegisterViewModel?.registerState?.value = "骚瑞 注册失败，错误信息是:${errorMsg}"
    }

    inner class ClickClass {
        // 点击事件，注册的函数
        fun registerAction() {
            if (mRegisterViewModel!!.userName.value.isNullOrBlank() || mRegisterViewModel!!.userPwd.value.isNullOrBlank()) {
                mRegisterViewModel?.registerState?.value = "用户名 或 密码 为空，请你好好检查"
                return
            }
            mRequestRegisterViewModel?.requestRegister(
                this@RegisterActivity,
                mRegisterViewModel!!.userName.value!!,
                mRegisterViewModel!!.userPwd.value!!,
                mRegisterViewModel!!.userPwd.value!!
            )
        }
    }
}