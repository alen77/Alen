package com.alen.alen.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.alen.alen.R
import org.feezu.liuli.timeselector.Utils.TextUtil

/**
 * Created by Alen on 2018/7/17.
 */
class AlertDialog(context: Context, themeId : Int) : Dialog(context, themeId) {

    class Builder(private val context: Context) {
        private var mDialog : AlertDialog ?= null

        private var mTitle : String ?= null
        private var mMessage : String ?= null
        private var mPositive : String ?= null
        private var mNegative: String ?= null
        private var mCancel : String ?= null
        private var mNegativeListener: OnNegativeClickListener ?= null
        private var mPositiveListener : OnNegativeClickListener ?= null

        private var withOffSize: Float = 0.toFloat()
        private var heightOffSize: Float = 0.toFloat()

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

        fun setPositive(listener: OnNegativeClickListener) : Builder {
            mPositiveListener = listener
            return this
        }

        fun setPositive(content: String, listener: OnNegativeClickListener) : Builder {
            mPositive = content
            mPositiveListener = listener
            return this
        }

        fun setNegative(content: String) : Builder {
            mNegative = content
            return this
        }

        fun setNegative(listener : OnNegativeClickListener) : Builder{
            mNegativeListener = listener
            return this
        }

        fun setNegative(content: String, listener : OnNegativeClickListener) : Builder{
            mNegative = content
            mNegativeListener = listener
            return this
        }


        fun setCancel(content: String) : Builder {
            mCancel = content
            return this
        }

        private fun setView(view : View) {
            var tvTitle : TextView = view.findViewById(R.id.tv_title)
            var tvMessage : TextView = view.findViewById(R.id.tv_message)
            var btnNegative : Button = view.findViewById(R.id.btn_negative)
            var btnPositive : Button = view.findViewById(R.id.btn_positive)
            if (!TextUtil.isEmpty(mTitle)) {
                tvTitle.text = mTitle
                tvTitle.visibility = View.VISIBLE
            }

            if (!TextUtil.isEmpty(mMessage)) {
                tvMessage.text = mMessage
                tvMessage.visibility = View.VISIBLE
            }

            if (mNegativeListener != null) {
                btnNegative.visibility = View.VISIBLE
                btnNegative.text = if (TextUtil.isEmpty(mNegative)) "negative" else mNegative
                btnNegative.setOnClickListener { _ ->
                    mNegativeListener!!.onNegative()
                    mDialog!!.dismiss()
                }
            }

            if (mPositiveListener != null) {
                btnPositive.visibility = View.VISIBLE
                btnPositive.text = if (TextUtil.isEmpty(mPositive)) "positive" else mPositive
                btnPositive.setOnClickListener { _ ->
                    mPositiveListener!!.onPositive()
                    mDialog!!.dismiss()
                }
            }
        }

        fun create() : AlertDialog? {
            mDialog = AlertDialog(context, R.style.time_dialog)

            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogLayoutView = inflater.inflate(R.layout.dialog_alert,
                    null)
            mDialog!!.addContentView(dialogLayoutView, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            mDialog!!.setContentView(dialogLayoutView)
            setView(dialogLayoutView)

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

    interface OnNegativeClickListener {
        fun onNegative()
        fun onPositive()
    }
}