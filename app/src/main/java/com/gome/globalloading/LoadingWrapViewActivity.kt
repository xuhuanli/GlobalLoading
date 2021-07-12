package com.gome.globalloading

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.gome.gloaballoading.Gloading
import com.gome.globalloading.databinding.ActivityWrapViewBinding
import kotlinx.coroutines.delay

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

class LoadingWrapViewActivity : BaseActivity() {
    private lateinit var binding: ActivityWrapViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWrapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setHolder(Gloading.mDefault.cover(binding.contentView).withRetry { onLoadRetry() })
        initData()
    }

    private fun initData() {
        lifecycleScope.launchWhenResumed {
            showLoading()
            delay(5000)
            showEmpty()
        }
    }

    override fun onLoadRetry() {
        super.onLoadRetry()
    }
}