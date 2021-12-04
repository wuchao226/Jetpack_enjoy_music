package com.wuc.music.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @author     wuchao
 * @date       2021/12/4 21:15
 * @desciption 此类被 IconBindingAdapter 使用了，因为 IconBindingAdapter 需要去 isPlaying（pauseView.play(); / pauseView.pause();）
 *  此类被 fragment_player.XML使用了，因为播放条需要 播放状态 与 暂停状态
 */
class PlayPauseView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {



    /**
     * 此时为待暂停标识
     */
    fun play() {

    }

    /**
     * 此时为为待播放标识
     */
    fun pause() {

    }
}