package com.wuc.music.ui.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.wuc.music.R
import com.wuc.music.bridge.state.PlayerViewModel
import com.wuc.music.databinding.FragmentPlayerBinding
import com.wuc.music.ui.base.BaseFragment

/**
 * @author : wuchao5
 * @date : 2021/12/3 19:59
 * @desciption : 底部播放条的 Fragment
 */
class PlayerFragment : BaseFragment() {

  private var mPlayerViewModel: PlayerViewModel? = null
  private var mBinding: FragmentPlayerBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 初始化 VM
    mPlayerViewModel = getFragmentViewModelProvider(this).get<PlayerViewModel>(PlayerViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // 加载界面
    val view: View = inflater.inflate(R.layout.fragment_player, container, false)

    // 绑定 Binding
    mBinding = FragmentPlayerBinding.bind(view)
    mBinding?.click = ClickProxy()
    mBinding?.event = EventHandler()
    mBinding?.vm = mPlayerViewModel
    return view
  }

  /**
   * 当我们点击的时候，我们要触发
   */
  inner class ClickProxy {

    /*public void playerMode() {
        PlayerManager.getInstance().changeMode();
    }*/

    fun previous() = PlayerManager.instance.playPrevious()

    operator fun next() = PlayerManager.instance.playNext()

    // 点击缩小的
    fun slideDown() = sharedViewModel.closeSlidePanelIfExpanded.setValue(true)

    //　更多的
    fun more() {}

    fun togglePlay() = PlayerManager.instance.togglePlay()

    fun playMode() = PlayerManager.instance.changeMode()

    fun showPlayList() = showShortToast("最近播放的细节，我没有搞...")
  }

  /**
   * 专门更新 拖动条进度相关的
   */
  inner class EventHandler : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    // 一拖动 松开手  把当前进度值 告诉PlayerManager
    override fun onStopTrackingTouch(seekBar: SeekBar) = PlayerManager.instance.setSeek(seekBar.progress)
  }
}