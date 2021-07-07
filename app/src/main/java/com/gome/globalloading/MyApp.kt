package com.gome.globalloading

import android.app.Application
import com.gome.gloaballoading.Gloading

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Gloading.initDefaultGloading(GlobalAdapter())
    }
}