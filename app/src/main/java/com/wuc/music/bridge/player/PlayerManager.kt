package com.wuc.music.bridge.player

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunminx.player.PlayerController
import com.kunminx.player.bean.base.BaseAlbumItem
import com.kunminx.player.bean.base.BaseArtistItem
import com.kunminx.player.bean.base.BaseMusicItem
import com.kunminx.player.bean.dto.ChangeMusic
import com.kunminx.player.bean.dto.PlayingMusic
import com.kunminx.player.contract.ICacheProxy
import com.kunminx.player.contract.IPlayController
import com.kunminx.player.contract.IServiceNotifier
import com.wuc.music.bridge.data.bean.TestAlbum
import com.wuc.music.bridge.player.notification.PlayerService


/**
 * 播放的管理器，由此类去调用，播放库来播放 等操作
 *
 * PlayerManager  ---> 播放库 播放 暂停 等等
 */
class PlayerManager private constructor() : IPlayController<TestAlbum, TestAlbum.TestMusic?> {

    private val mController: PlayerController<TestAlbum, TestAlbum.TestMusic> = PlayerController()

    private var mContext: Context? = null


    fun init(context: Context) {
        init(context, null, null)
    }

    override fun init(context: Context?, iServiceNotifier: IServiceNotifier?, iCacheProxy: ICacheProxy?) {
        mContext = context?.applicationContext
        //添加额外的音乐格式
        val extraFormats: MutableList<String> = ArrayList()
        extraFormats.add(".flac")
        extraFormats.add(".ape")
        mController.init(mContext, extraFormats, { startOrStop: Boolean ->
            val intent = Intent(mContext, PlayerService::class.java)
            if (startOrStop) {
                mContext!!.startService(intent)
            } else {
                mContext!!.stopService(intent)
            }
        },null)

        /* mController.init(mContext, null) { startOrStop: Boolean ->
             val intent = Intent(mContext, PlayerService::class.java)
             if (startOrStop) {
                 mContext?.startService(intent) // 后台播放
             } else {
                 mContext?.stopService(intent) // 停止后台播放
             }
         }*/
    }

    override fun loadAlbum(musicAlbum: TestAlbum) {
        mController.loadAlbum(musicAlbum)
    }

    override fun loadAlbum(musicAlbum: TestAlbum, playIndex: Int) {
        mController.loadAlbum(musicAlbum, playIndex)
    }

    override fun playAudio() {
        mController.playAudio()
    }

    // 下标的 播放
    override fun playAudio(albumIndex: Int) {
        // 在这里只需要知道，调用此 playAudio函数，就能够播放音乐了
        mController.playAudio(albumIndex)
    }

    // 下一首播放
    override fun playNext() {
        mController.playNext()
    }

    // 上一首播放
    override fun playPrevious() {
        mController.playPrevious()
    }

    override fun playAgain() {
        mController.playAgain()
    }

    // 暂停 播放
    override fun pauseAudio() {
        mController.pauseAudio()
    }

    override fun resumeAudio() {
        mController.resumeAudio()
    }

    // 清除歌曲下标 的 标记
    override fun clear() {
        mController.clear()
    }

    override fun changeMode() {
        mController.changeMode()
    }

    override fun isPlaying(): Boolean {
        return mController.isPlaying
    }

    override fun isPaused(): Boolean {
        return mController.isPaused
    }


    override fun isInit(): Boolean {
        return mController.isInit
    }

    override fun requestLastPlayingInfo() {
        mController.requestLastPlayingInfo()
    }

    override fun setSeek(progress: Int) {
        mController.setSeek(progress)
    }

    override fun getTrackTime(progress: Int): String {
        return mController.getTrackTime(progress)
    }


    override fun getAlbum(): TestAlbum? {
        return mController.album
    }

    override fun getAlbumMusics(): List<TestAlbum.TestMusic?> {
        return mController.albumMusics
    }

    override fun setChangingPlayingMusic(changingPlayingMusic: Boolean) {
        mController.setChangingPlayingMusic(changingPlayingMusic)
    }

    override fun getAlbumIndex(): Int {
        return mController.albumIndex
    }

    /* // 返回 音乐播放的 ld，上一首，下一首， 你只要敢改变 音乐，就可以被人家观察
     override fun getChangeMusicLiveData(): MutableLiveData<ChangeMusic<*, *, *>> {
         return mController.changeMusicLiveData
     }

     // 音乐数据，也需要观察
     override fun getPlayingMusicLiveData(): MutableLiveData<PlayingMusic<*, *>> {
         return mController.playingMusicLiveData
     }

     override fun getPauseLiveData(): MutableLiveData<Boolean> {
         return mController.pauseLiveData
     }

     // 播放模式：列表循环，单曲循环，随机播放
     override fun getPlayModeLiveData(): MutableLiveData<Enum<*>> {
         return mController.playModeLiveData
     }*/

    // 返回 音乐播放的 ld，上一首，下一首， 你只要敢改变 音乐，就可以被人家观察
    override fun getChangeMusicEvent(): LiveData<ChangeMusic<BaseAlbumItem<BaseMusicItem<BaseArtistItem>, BaseArtistItem>, BaseMusicItem<BaseArtistItem>, BaseArtistItem>> {
        return mController.changeMusicEvent
    }

    // 音乐数据，也需要观察
    override fun getPlayingMusicEvent(): LiveData<PlayingMusic<BaseArtistItem, BaseAlbumItem<BaseMusicItem<BaseArtistItem>, BaseArtistItem>, BaseMusicItem<BaseArtistItem>>> {
        return mController.playingMusicEvent
    }

    override fun getPauseEvent(): LiveData<Boolean> {
        return mController.pauseEvent
    }

    // 播放模式：列表循环，单曲循环，随机播放
    override fun getPlayModeEvent(): LiveData<Enum<*>> {
        return mController.playModeEvent
    }

    override fun getRepeatMode(): Enum<*>? {
        return mController.repeatMode
    }

    override fun togglePlay() {
        mController.togglePlay()
    }

    override fun getCurrentPlayingMusic(): TestAlbum.TestMusic {
        return mController.currentPlayingMusic
    }

    companion object {
        // 单例模式
        @SuppressLint("StaticFieldLeak")
        val instance = PlayerManager() // 单例相关的
    }

}