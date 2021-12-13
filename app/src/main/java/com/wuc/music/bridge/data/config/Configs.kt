package com.wuc.music.bridge.data.config

import android.os.Environment
import com.wuc.architecture.utils.Utils

object Configs {

    // 封面路径地址
    @JvmField
    val COVER_PATH: String = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath

    const val TAG = "wuc"
}