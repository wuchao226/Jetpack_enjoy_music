package com.wuc.music.data.binding

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter
import com.wuc.architecture.utils.Utils

/**
 *
 * 展示WebView的适配器
 * 各项参数设置
 *
 * 和fragment_main.xml里面的pageAssetPath / loadPage挂钩的
 */
object WebViewBindingAdapter {
  @JvmStatic
  @SuppressLint("SetJavaScriptEnabled")
  @BindingAdapter(value = ["pageAssetPath"], requireAll = false)
  fun loadAssetsPage(webView: WebView, assetPath: String) {
    webView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
      ): Boolean {
        val uri = request.url
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        Utils.getApp().startActivity(intent)
        return true
      }
    }
    webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    val webSettings = webView.settings
    webSettings.javaScriptEnabled = true
    webSettings.defaultTextEncodingName = "UTF-8"
    webSettings.setSupportZoom(true)
    webSettings.builtInZoomControls = true
    webSettings.displayZoomControls = false
    webSettings.useWideViewPort = true
    webSettings.loadWithOverviewMode = true
    val url = "file:///android_asset/$assetPath"
    webView.loadUrl(url)
  }

  @SuppressLint("SetJavaScriptEnabled")
  @BindingAdapter(value = ["loadPage"], requireAll = false)
  fun loadPage(webView: WebView, loadPage: String) {
    webView.webViewClient = WebViewClient()
    webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    val webSettings = webView.settings
    webSettings.javaScriptEnabled = true
    webSettings.defaultTextEncodingName = "UTF-8"
    webSettings.setSupportZoom(true)
    webSettings.builtInZoomControls = true
    webSettings.displayZoomControls = false
    webSettings.useWideViewPort = true
    webSettings.loadWithOverviewMode = true
    webView.loadUrl(loadPage)
  }
}