package com.slim.http.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import com.slim.http.delegate.http
import com.slim.http.delegate.zip
import com.slim.http.intes.PageWidgetInterface
import com.slim.http.intes.WidgetInterface
import com.slim.http.sample.helper.LoadingDialog
import com.slim.http.sample.repository.HttpRepository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), WidgetInterface, PageWidgetInterface {
    override fun showEmptyView(isSuccess: Boolean) {
    }

    override fun loadData() {
    }

    override fun stopRefresh(isSuccess: Boolean) {
    }

    override fun addItems(items: Any?) {
        items?.let {
            println(it.toString())
        }
    }

    override fun clearLoadMoreListener() {
    }

    override fun getPageMap(): Map<String, String> {
        return ArrayMap<String, String>()
    }

    private lateinit var xx: LoadingDialog

    override fun getRootLayoutId() = R.layout.activity_main

    override fun getContentLayoutId() = R.layout.activity_main
    override fun initRootView() {
    }

    override fun initRootData() {
    }


    override fun initData() {
        xx = LoadingDialog(this)
    }

    override fun bindListener() {

    }

    override fun onProcessLogic() {

    }

    override fun showLoadingView() {
        xx.show()
    }

    override fun hideLoadingView() {
        xx.hide()
    }

    override fun toastSuccess(message: CharSequence?) {

    }

    override fun toastFail(message: CharSequence?) {

    }

    override fun showErrorView(message: CharSequence?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showContentView() {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        test.setOnClickListener { test1() }
    }


    private fun test1() {
        val param = ArrayMap<String, String>()
        param["app_type"] = "1"
        param["uuid"] = ""
        val apiService = HttpRepository.getApiService()
        val weather1 = apiService.join()
        val weather2 = apiService.notJoin()
        zip(this) {
            mutableListOf(weather1, weather2)
        }.onSucess {
            test.text = "$it"
        }
        http(this) {
            weather1
        }.onSucess {

        }
    }
}
