package com.wuc.music.data.binding

import androidx.databinding.BindingAdapter
import com.wuc.music.ui.view.PlayPauseView
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import net.steamcrafted.materialiconlib.MaterialIconView

/**
 * @author     wuchao
 * @date       2021/12/4 17:02
 * @desciption 和fragment_player.xml里面的 setIcon / isPlaying 挂钩
 */
object IconBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["isPlaying"], requireAll = false)
    fun isPlaying(pauseView: PlayPauseView, isPlaying: Boolean) {
        if (isPlaying) {
            pauseView.play()
        } else {
            pauseView.pause()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["mdIcon"], requireAll = false)
    fun setIcon(view: MaterialIconView, iconValue: MaterialDrawableBuilder.IconValue?) {
        view.setIcon(iconValue)
    }
}