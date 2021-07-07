package com.gome.globalloading

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gome.gloaballoading.Gloading

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

open class BaseActivity : AppCompatActivity() {
    private val mHolder by lazy {
        Gloading.mDefault.wrap(this).withRetry {
            onLoadRetry()
        }
    }

    protected fun onLoadRetry() {
        Log.d("TAG", "运行重试invoked ")
    }

    fun showLoading() {
        mHolder.showLoading()
    }

    fun showLoadSuccess() {
        mHolder.showLoadSuccess()
    }

    fun showLoadFailed() {
        mHolder.showLoadFailed()
    }

    fun showEmpty() {
        mHolder.showEmpty()
    }
}