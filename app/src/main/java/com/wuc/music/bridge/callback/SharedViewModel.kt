package com.wuc.music.bridge.callback

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wuc.architecture.bridge.callback.UnPeekLiveData
import com.wuc.music.bridge.data.login_register.LoginSession
import java.util.*

/**
 * @author : wuchao5
 * @date : 2021/12/2 19:30
 * @desciption : SharedViewModel 的职责是用于 页面通信的
 */
class SharedViewModel : ViewModel() {

    companion object {
        // 存放记录，打开过“搜索界面”就会记录下来，owner.getClass().getSimpleName():SearchFragment / owner.getClass().getSimpleName():SearchFragment
        val TAG_OF_SECONDARY_PAGES: List<String> = ArrayList()
    }

    /**
     * 添加监听（可以弹上来的监听）
     * 是为了 sliding.addPanelSlideListener(new PlayerSlideListener(mBinding, sliding));
     */
    val timeToAddSlideListener = UnPeekLiveData<Boolean>()

    /**
     * 控制 播放详情 点击/back 掉下来
     * 点击“播放条”弹上来
     * 播放详情中 左手边滑动图标(点击的时候)，与 MainActivity back 是 会set  ----> 如果是扩大，也就是 详情页面展示了出来
     */
    val closeSlidePanelIfExpanded = UnPeekLiveData<Boolean>()

    /**
     * 活动关闭的一些记录
     */
    val activityCanBeClosedDirectly = UnPeekLiveData<Boolean>()

    /**
     * openMenu打开菜单的时候会 set触发---> 改变 openDrawer.setValue(aBoolean); 的值
     */
    val openOrCloseDrawer = UnPeekLiveData<Boolean>()

    /**
     *  开启和关闭 卡片相关的状态，如果发生改变 会和 allowDrawerOpen 挂钩
     */
    val enableSwipeDrawer = UnPeekLiveData<Boolean>()


    /**
     * 保存登录信息的临时会话 需要贯穿整个项目，所以是共享的   UnPeekLiveData<Session>()
     */
    val session = MutableLiveData<LoginSession>()
}