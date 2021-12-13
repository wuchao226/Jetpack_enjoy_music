package com.wuc.music.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import com.wuc.music.R

/**
 * @author     wuchao
 * @date       2021/12/4 16:55
 * @desciption 对主界面重要的自定义 ImageView，就是播放大图标
 */
@RequiresApi(Build.VERSION_CODES.M)
class ForegroundImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    constructor(context: Context) : this(context, null)

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ForegroundImageView)
        val foreground = a.getDrawable(R.styleable.ForegroundImageView_android_foreground)
        foreground?.let { setForeground(it) }
        a.recycle()
    }

    /**
     * Supply a drawable resource that is to be rendered on top of all of the child
     * views in the frame layout.
     *
     * @param drawableResId The drawable resource to be drawn on top of the children.
     */
    fun setForegroundResource(drawableResId: Int) {
        foreground = context.resources.getDrawable(drawableResId)
    }

    /**
     * Supply a Drawable that is to be rendered on top of all of the child
     * views in the frame layout.
     *
     * @param drawable The Drawable to be drawn on top of the children.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun setForeground(drawable: Drawable) {
        if (foreground === drawable) {
            return
        }
        if (foreground != null) {
            foreground.callback = null
            unscheduleDrawable(foreground)
        }
        foreground = drawable
        if (drawable != null) {
            drawable.callback = this
            if (drawable.isStateful) {
                drawable.state = drawableState
            }
        }
        requestLayout()
        invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who === foreground
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        if (foreground != null) {
            foreground.jumpToCurrentState()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (foreground != null && foreground.isStateful) {
            foreground.state = drawableState
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (foreground != null) {
            foreground.setBounds(0, 0, measuredWidth, measuredHeight)
            invalidate()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (foreground != null) {
            foreground.setBounds(0, 0, w, h)
            invalidate()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (foreground != null) {
            foreground.draw(canvas!!)
        }
    }
}