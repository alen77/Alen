package com.alen.alen.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Alen on 2018/7/17.
 */
class BaseDialogBuilder(private val context: Context){
    private var mDialog : BaseDialog ?= null

    private var mTitle : String ?= null
    private var mMessage : String ?= null
    private var mPositive : String ?= null
    private var mNegative: String ?= null
    private var mCancel : String ?= null

    private var mContentResId : Int = 0

    fun setContentView(resId : Int): BaseDialogBuilder {
        mContentResId = resId
        return this
    }

    fun setTitle(title: String) : BaseDialogBuilder {
        mTitle = title
        return this
    }

    fun setMessage(message: String) : BaseDialogBuilder {
        mMessage = message
        return this
    }

    fun setPositive(content: String) : BaseDialogBuilder {
        mPositive = content
        return this
    }

    fun setNegative(content: String) : BaseDialogBuilder {
        mNegative = content
        return this
    }

    fun setCancel(content: String) : BaseDialogBuilder {
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

        return mDialog
    }
}