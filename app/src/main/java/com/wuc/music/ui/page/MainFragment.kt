package com.wuc.music.ui.page

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wuc.architecture.ui.adapter.SimpleBaseBindingAdapter
import com.wuc.music.R
import com.wuc.music.bridge.request.MusicRequestViewModel
import com.wuc.music.bridge.state.MainViewModel
import com.wuc.music.bridge.data.bean.TestAlbum
import com.wuc.music.bridge.player.PlayerManager
import com.wuc.music.databinding.AdapterPlayItemBinding
import com.wuc.music.databinding.FragmentMainBinding
import com.wuc.music.ui.base.BaseFragment


class MainFragment : BaseFragment() {

    private var mMainBinding: FragmentMainBinding? = null

    /**
     * 首页 Fragment 的 ViewModel
     */
    private var mMainViewModel: MainViewModel? = null

    /**
     * 音乐资源相关的VM  todo Request ViewModel
     */
    private var mMusicRequestViewModel: MusicRequestViewModel? = null

    /**
     * 适配器
     */
    private var mAdapter: SimpleBaseBindingAdapter<TestAlbum.TestMusic?, AdapterPlayItemBinding?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel = getFragmentViewModelProvider(this).get(MainViewModel::class.java)
        mMusicRequestViewModel = getFragmentViewModelProvider(this).get(MusicRequestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        mMainBinding = FragmentMainBinding.bind(view)
        // 设置点击事件，布局就可以直接绑定
        mMainBinding?.click = ClickProxy()
        // 设置VM，就可以实时数据变化
        mMainBinding?.vm = mMainViewModel
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 触发  --->   MainFragment初始化页面的标记，初始化选项卡和页面
        mMainViewModel?.initTabAndPage?.set(true)

        // 触发，---> 还要加载WebView

        mMainViewModel?.pageAssetPath?.set("JetPack之 WorkManager.html")

        // 展示数据，适配器里面的的数据 展示出来
        // 设置设配器(item的布局 和 适配器的绑定)
        mAdapter = object : SimpleBaseBindingAdapter<TestAlbum.TestMusic?, AdapterPlayItemBinding?>(context, R.layout.adapter_play_item) {
            override
            fun onSimpleBindItem(binding: AdapterPlayItemBinding?, item: TestAlbum.TestMusic?, holder: RecyclerView.ViewHolder?) {
                binding?.tvTitle?.text = item?.title // 标题
                binding?.tvArtist?.text = item?.artist?.name // 歌手 就是 艺术家
                Glide.with(binding?.ivCover!!.context).load(item?.coverImg).into(binding.ivCover) // 左右边的图片

                // 歌曲下标记录
                val currentIndex = PlayerManager.instance.albumIndex // 歌曲下标记录

                // 播放的标记
                // 播放的时候，右变状态图标就是红色， 如果对不上的时候，就是没有
                binding.ivPlayStatus.setColor(
                    if (currentIndex == holder?.adapterPosition) resources.getColor(R.color.my_c2) else Color.TRANSPARENT
                )

                // 点击Item
                binding.root.setOnClickListener { v ->
                    Toast.makeText(mContext, "播放音乐", Toast.LENGTH_SHORT).show()
                    PlayerManager.instance.playAudio(holder!!.adapterPosition)
                }
            }
        }
        mMainBinding?.rv?.adapter = mAdapter

        // 播放相关业务的数据（如果这个数据发生了改变，为了更好的体验） 眼睛 盯着
        PlayerManager.instance.playingMusicEvent.observe(viewLifecycleOwner, {
            mAdapter?.notifyDataSetChanged() // 刷新适配器
        })

        // 请求数据
        // 保证我们列表没有数据（music list）
        if (PlayerManager.instance.album == null) {
            mMusicRequestViewModel?.requestFreeMusics()
        }


        // 眼睛 监听的变化，你只要敢变，UI就要变
        // 观察到了 observe
        // 音乐资源的 VM
        // 此处理解就是观察者， 有一双眼睛盯着看，getFreeMusicsLiveData变化了，如果变化就执行
        mMusicRequestViewModel?.freeMusicesLiveData?.observe(viewLifecycleOwner, Observer { musicAlbum: TestAlbum? ->
            if (musicAlbum != null && musicAlbum.musics != null) {
                // 这里特殊：直接更新UI，越快越好
                mAdapter?.list = musicAlbum.musics // 数据加入适配器
                mAdapter?.notifyDataSetChanged()

                // 播放相关的业务需要这个数据
                if (PlayerManager.instance.album == null ||
                    PlayerManager.instance.album!!.albumId != musicAlbum.albumId
                ) {
                    PlayerManager.instance.loadAlbum(musicAlbum)
                }
            }
        })
    }

    inner class ClickProxy {
        // 当在首页点击 “菜单” 的时候，直接导航到 ---> 菜单的Fragment界面
        fun openMenu() {
            mSharedViewModel.openOrCloseDrawer.value = true
        } // 触发

        // 当在首页点击 “搜索图标” 的时候，直接导航到 ---> 搜索的Fragment界面
        fun search() = nav().navigate(R.id.action_mainFragment_to_searchFragment)
    }
}