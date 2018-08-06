package com.alen.alen.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

/**
 * Created by Alen on 2018/7/12.
 */
class StretchLayout : LinearLayout {

    constructor(mContext: Context) : this(mContext, null)
    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs!!, 0)

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr)

    private var mIsBeginDragged = false

    private var mIsScorllDown = false

    private var mInitX = 0f

    private var mInitY = 0f

    private var mEndY = 0

    private var mHeaderWidth = 0

    private var mHeaderHeight = 0

    private var mHeaderView : View ?= null

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mInitX = ev.x
                mInitY = ev.y
                mIsBeginDragged = false
            }
            MotionEvent.ACTION_MOVE -> {
                var diffX = ev.x - mInitX
                var diffY = ev.y - mInitY
                if (diffY / Math.abs(diffX) > 2) {
                    mIsBeginDragged = diffY > 0
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (mHeaderWidth == 0) {
            mHeaderWidth = mHeaderView!!.width
            mHeaderHeight = mHeaderView!!.height
        }
        when(event!!.action) {
            MotionEvent.ACTION_MOVE -> {
                if (mIsBeginDragged) {
                    var diffY = event.y - mInitY
                    if (diffY > 0) {
                        changeHeader(event.y - mInitY - mEndY)
                        return true
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mIsBeginDragged) {
                    mEndY = (event.y - mInitY).toInt()
                    reset()
                    return true
                }
            }
            MotionEvent.ACTION_CANCEL -> null
        }
        return super.onTouchEvent(event)
    }

    fun setHeader(header : View) {
        mHeaderView = header
    }

    private fun reset() {
        mIsBeginDragged = false
        mHeaderView!!.postDelayed({

            while (mEndY > 0) {
                mEndY -= 10
                changeHeader(Math.abs(mEndY.toFloat()))
                reset()
            }
        }, 100)
    }
    private fun changeHeader(float: Float) {
        val pullOffset = Math.pow(float.toDouble(), 0.8)
        val newHeight = pullOffset + mHeaderHeight
        val newWidth = ((newHeight / mHeaderHeight) * mHeaderWidth)
        mHeaderView!!.layoutParams.height = newHeight.toInt()
        mHeaderView!!.layoutParams.width = newWidth.toInt()
        val margin = (newWidth - mHeaderWidth) / 2
        mHeaderView!!.translationX = (- margin).toFloat()
        mHeaderView!!.requestLayout()
    }

    private fun resetHeader() {
        mHeaderView!!.layoutParams.height = mHeaderHeight
        mHeaderView!!.layoutParams.width = mHeaderWidth
        mHeaderView!!.translationX = 0f
        mHeaderView!!.requestLayout()
    }
}