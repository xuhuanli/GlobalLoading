package com.gome.globalloading

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import com.gome.gloaballoading.GAdapter
import com.gome.gloaballoading.Gloading

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

class GlobalAdapter : GAdapter {
    override fun getView(holder: Gloading.Holder, convertView: View?, status: Int): View {
        fun inflateView(@LayoutRes id: Int) = LayoutInflater.from(holder.getContext())
            .inflate(id, holder.getWrapper(), false)

        val view = when (status) {
            Gloading.STATUS_LOADING -> inflateView(R.layout.status_loading)
            Gloading.STATUS_LOAD_SUCCESS -> {
                inflateView(R.layout.status_success).apply { visibility = View.GONE }
            }
            Gloading.STATUS_LOAD_FAILED -> inflateView(R.layout.status_fail)
            Gloading.STATUS_EMPTY_DATA -> inflateView(R.layout.status_empty)
            else -> throw RuntimeException("未定义的Loading状态")
        }
        return view.apply {
            setBackgroundColor(Color.parseColor("#FFF0F0F0"))
        }
    }
}