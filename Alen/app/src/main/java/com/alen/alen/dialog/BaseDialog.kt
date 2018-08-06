package com.alen.alen.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Alen on 2018/7/16.
 */
open class BaseDialog : Dialog{

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    constructor(context: Context, cancelAble: Boolean,
                cancelListener: DialogInterface.OnCancelListener ) : super(context, cancelAble, cancelListener)

    class Builder(private val context: Context) {
        private var mDialog : BaseDialog ?= null

        private var mTitle : String ?= null
        private var mMessage : String ?= null
        private var mPositive : String ?= null
        private var mNegative: String ?= null
        private var mCancel : String ?= null

        private var withOffSize: Float = 0.toFloat()
        private var heightOffSize: Float = 0.toFloat()

        private var mContentResId : Int = 0

        fun setContentView(resId : Int): Builder {
            mContentResId = resId
            return this
        }

        fun setTitle(title: String) : Builder {
            mTitle = title
            return this
        }

        fun setMessage(message: String) : Builder {
            mMessage = message
            return this
        }

        fun setPositive(content: String) : Builder {
            mPositive = content
            return this
        }

        fun setNegative(content: String) : Builder {
            mNegative = content
            return this
        }

        fun setCancel(content: String) : Builder {
            mCancel = content
            return this
        }

        fun create() : BaseDialog? {
            mDialog = BaseDialog(context)

            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogLayoutView = inflater.inflate(mContentResId,
                    null)
            mDialog!!.addContentView(dialogLayoutView, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            mDialog!!.setContentView(dialogLayoutView)

            val window = mDialog!!.window
            val context = this.context as Activity
            val windowManager = context.windowManager

            val defaultDisplay = windowManager.defaultDisplay

            val attributes = window!!.attributes

            if (withOffSize.toDouble() != 0.0) {

                attributes.width = (defaultDisplay.width * withOffSize).toInt()
            } else {
                attributes.width = (defaultDisplay.width * 0.8).toInt()

            }
            if (heightOffSize.toDouble() != 0.0) {

                attributes.height = (defaultDisplay.height * heightOffSize).toInt()
            }
            window.attributes = attributes

            return mDialog
        }
    }

}