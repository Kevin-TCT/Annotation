package com.kevintu.annotation

import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.kevintu.annotation.base.BaseActivity

class MainActivity2 : BaseActivity() {

    @BindView(R.id.result) lateinit var resultView: TextView
    @BindView(R.id.result_edit) lateinit var resultEditView: TextView
    @BindView(R.id.btn) lateinit var btnView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
    }

    override fun openButterKnife() : Boolean {
        return true
    }

    @OnClick(R.id.btn)
    fun btnOnclick() {
        resultView.text = "clicked"
        resultEditView.text = "clicked"
    }
}
