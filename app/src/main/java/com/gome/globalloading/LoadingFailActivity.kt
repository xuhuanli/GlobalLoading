package com.gome.globalloading

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.gome.globalloading.databinding.ActivityLoadingFailBinding
import kotlinx.coroutines.delay

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

class LoadingFailActivity: BaseActivity() {
    private lateinit var binding: ActivityLoadingFailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingFailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
    }

    private fun initData() {
        lifecycleScope.launchWhenStarted {
            showLoading()
            delay(5000)
            showLoadFailed()
        }
    }
}