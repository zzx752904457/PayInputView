package com.zzx.custom.payinputview

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.dialog_password.*


class PasswordDialog(private val activity: Activity) : Dialog(activity, R.style.AskDialogStyle) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_password)
        keyboard_view.bindPasswordView(password_view)
    }

    override fun show() {
        super.show()
        val params = window?.attributes
        params?.width = getScreenWidth()
        params?.height = getScreenHeight()
        window?.attributes = params
    }

    private fun getScreenWidth(): Int {
        val manager = activity.windowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        val manager = activity.windowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }
}