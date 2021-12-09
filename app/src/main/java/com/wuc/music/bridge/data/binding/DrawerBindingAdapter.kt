package com.wuc.music.bridge.data.binding

import androidx.core.view.GravityCompat
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout

/**
 * @author : wuchao5
 * @date : 2021/12/3 16:17
 * @desciption : 和 activity_main.xml 里面的 allowDrawerOpen 和 openDrawer 挂钩的
 */
object DrawerBindingAdapter {

  /**
   * 打开抽屉 与 关闭抽屉
   */
  @JvmStatic
  @BindingAdapter(value = ["isOpenDrawer"], requireAll = false)
  fun openDrawer(drawerLayout: DrawerLayout, isOpenDrawer: Boolean) {
    if (isOpenDrawer && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.openDrawer(GravityCompat.START)
    } else {
      drawerLayout.closeDrawer(GravityCompat.START)
    }
  }

  /**
   * 允许抽屉打开 与 关闭
   */
  @JvmStatic
  @BindingAdapter(value = ["allowDrawerOpen"], requireAll = false)
  fun allowDrawerOpen(drawerLayout: DrawerLayout, allowDrawerOpen: Boolean) {
    drawerLayout.setDrawerLockMode(if (allowDrawerOpen) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
  }
}