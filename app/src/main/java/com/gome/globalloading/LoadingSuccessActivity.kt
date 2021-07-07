package com.gome.globalloading

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.gome.globalloading.databinding.ActivityLoadingSuccessBinding
import kotlinx.coroutines.delay

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

class LoadingSuccessActivity : BaseActivity() {
    private lateinit var binding: ActivityLoadingSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
    }

    private fun initData() {
        lifecycleScope.launchWhenStarted {
            showLoading()
            delay(5000)
            binding.tvSuccessData.text = "set new text"
            showLoadSuccess()
        }
    }
}