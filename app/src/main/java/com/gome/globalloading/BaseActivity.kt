package com.gome.globalloading

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gome.gloaballoading.Gloading

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

open class BaseActivity : AppCompatActivity() {
    protected lateinit var mHolder: Gloading.Holder

    private fun initLoadingStatusViewIfNeed() {
        if (!this::mHolder.isInitialized) {
            setHolder(Gloading.mDefault.wrap(this).withRetry {
                onLoadRetry()
            })
        }
    }

    protected fun setHolder(holder: Gloading.Holder) {
        mHolder = holder
    }

    protected open fun onLoadRetry() {
        Log.d("TAG", "运行重试invoked ")
    }

    fun showLoading() {
        initLoadingStatusViewIfNeed()
        mHolder.showLoading()
    }

    fun showLoadSuccess() {
        initLoadingStatusViewIfNeed()
        mHolder.showLoadSuccess()
    }

    fun showLoadFailed() {
        initLoadingStatusViewIfNeed()
        mHolder.showLoadFailed()
    }

    fun showEmpty() {
        initLoadingStatusViewIfNeed()
        mHolder.showEmpty()
    }
}