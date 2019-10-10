package com.kevintu.annotation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Create by Kevin-Tu on 2019/10/10.
 */
open class BaseActivity : AppCompatActivity() {

    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (openButterKnife()) {
            unbinder = ButterKnife.bind(this)
        }
    }

    open fun openButterKnife() : Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder?.unbind()
    }
}