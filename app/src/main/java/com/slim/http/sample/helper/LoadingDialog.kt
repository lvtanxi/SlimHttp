package com.slim.http.sample.helper

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import com.slim.http.sample.R
import kotlinx.android.synthetic.main.widget_dialog_loading.*


class LoadingDialog (context: Context) : Dialog(context, R.style.loading_dialog_style) {

    private val outSideCancele = false
    private var animationDrawable : AnimationDrawable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widget_dialog_loading)
        setCanceledOnTouchOutside(outSideCancele)
        animationDrawable=custom_ProgressBar.drawable as AnimationDrawable
    }

    override fun show() {
        super.show()
        animationDrawable?.start()
    }

    override fun dismiss() {
        super.dismiss()
        animationDrawable?.stop()
    }

}
