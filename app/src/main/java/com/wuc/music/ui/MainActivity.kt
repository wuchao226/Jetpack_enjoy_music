package com.wuc.music.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.wuc.music.R
import com.wuc.music.bridge.state.MainActivityViewModel
import com.wuc.music.bridge.state.MainViewModel
import com.wuc.music.databinding.ActivityMainBinding
import com.wuc.music.ui.base.BaseActivity

/**
 * @author : wuchao5
 * @date : 2021/12/3 15:28
 * @desciption : 主页  管理者
 */
class MainActivity : BaseActivity() {

    private var mMainActivityBinding: ActivityMainBinding? = null
    private var mMainActivityViewModel: MainActivityViewModel? = null

    // 被倾听 为了扩展
    private var isListened = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityViewModel = getActivityViewModelProvider(this).get(MainActivityViewModel::class.java)
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mMainActivityBinding?.lifecycleOwner = this
        mMainActivityBinding?.vm = mMainActivityViewModel

        // 间接的可以打开菜单 （观察 发送打开菜单的指令）
        mSharedViewModel.openOrCloseDrawer.observe(this, {
            // 触发，就会改变，---> 观察（打开菜单逻辑）
            mMainActivityViewModel?.openDrawer?.value = it
        })

        // 眼睛 3 监听 （发送 打开菜单的指令 1）
        mSharedViewModel.enableSwipeDrawer.observe(this, { aBoolean ->

            // 做很多很多的 过滤 检查 工作
            // ....
            // 触发抽屉控件关联的值
            mMainActivityViewModel?.allowDrawerOpen?.value = aBoolean
        })

        // mMainActivityViewModel 的 变化 先暂停  (抽屉控件干了，我就不需要干了)
        /* mMainActivityViewModel?.openDrawer?.observe(this, {

         })*/

        // 共享 （观察）
        mSharedViewModel.activityCanBeClosedDirectly.observe(this, {
            // 先不写，作用不大
        })
    }

    /**
     * 详情看：https://www.cnblogs.com/lijunamneg/archive/2013/01/19/2867532.html
     * 这个onWindowFocusChanged指的是这个Activity得到或者失去焦点的时候 就会call。。
     * 也就是说 如果你想要做一个Activity一加载完毕，就触发什么的话 完全可以用这个！！！
     *  entry: onStart---->onResume---->onAttachedToWindow----------->onWindowVisibilityChanged--visibility=0---------->onWindowFocusChanged(true)------->
     * @param hasFocus
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!isListened) {
            // 此字段只要发生了改变，就会 添加监听（可以弹上来的监听）
            mSharedViewModel.timeToAddSlideListener.value = true // 触发改变
            isListened = true // 被倾听 修改成true
        }
    }

    /**
     * https://www.jianshu.com/p/d54cd7a724bc
     * Android中在按下back键时会调用到onBackPressed()方法，
     * onBackPressed相对于finish方法，还做了一些其他操作，
     * 而这些操作涉及到Activity的状态，所以调用还是需要谨慎对待。
     */
    override fun onBackPressed() {
//        super.onBackPressed()
        // 如果把下面的代码注释掉，back键时，不会把播放详情给掉下来
        mSharedViewModel.closeSlidePanelIfExpanded.value = true // 触发改变 true 如果此时是 播放详情，会被掉下来
    }
}