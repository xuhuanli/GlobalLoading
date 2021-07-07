package com.gome.gloaballoading

import android.app.Activity
import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 *  @author xuhuanli2017@gmail.com
 *
 */

class Gloading private constructor() {
    companion object {
        const val STATUS_LOADING = 1
        const val STATUS_LOAD_SUCCESS = 2
        const val STATUS_LOAD_FAILED = 3
        const val STATUS_EMPTY_DATA = 4

        /**
         * 创建新的全局显示类
         *
         * @param adapter 自定义的GAdapter
         * @return 一个新的Gloading实例
         */
        fun createNewGloading(adapter: GAdapter): Gloading {
            val gloading = Gloading()
            gloading.mAdapter = adapter
            return gloading
        }

        val mDefault by lazy {
            Gloading()
        }

        fun initDefaultGloading(adapter: GAdapter) {
            mDefault.mAdapter = adapter
        }
    }

    private lateinit var mAdapter: GAdapter

    /**
     * loading样式会包裹着整个content区域
     *
     * @param activity
     * @return Holder
     */
    fun wrap(activity: Activity): Holder {
        val wrapper = activity.findViewById<ViewGroup>(android.R.id.content)
        return Holder(mAdapter, activity, wrapper)
    }

    /**
     * 对传入的view添加一层FrameLayout包装
     *
     * @param view 需要被显示loading的view
     * @return Holder
     */
    fun wrap(view: View): Holder {
        val wrapper = FrameLayout(view.context)
        // FrameLayout将使用你传入的view的布局参数
        val layoutParams = view.layoutParams
        layoutParams?.let { wrapper.layoutParams = it }
        // 用wrapper替换传入的view
        val parent = view.parent as? ViewGroup
        parent?.let {
            val index = it.indexOfChild(view)
            parent.removeView(view)
            parent.addView(wrapper, index)
        }
        // 用wrapper包装传入的view
        val replaceLp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
        )
        wrapper.addView(view, replaceLp)
        return Holder(mAdapter, view.context, wrapper)
    }

    /**
     * Gloading 显示会覆盖在传入的view上 RelativeLayout / ConstraintLayout时推荐使用
     *
     * @param view
     * @return Holder
     */
    fun cover(view: View): Holder {
        val parent = view.parent
            ?: throw RuntimeException(message = "view has no parent to show gloading as cover!")
        val viewGroup = parent as ViewGroup
        val coverLayout = FrameLayout(view.context)
        viewGroup.addView(coverLayout, view.layoutParams)
        return Holder(mAdapter, view.context, coverLayout)
    }

    class Holder(
        private val mAdapter: GAdapter,
        private val mContext: Context,
        private val mWrapper: ViewGroup
    ) {
        private var mRetryTask: (() -> Unit)? = null
        private var mData: Any? = null
        private var curState: Int = 0
        private var mCurStatusView: View? = null
        // 缓存四个状态的view
        private val mStatusViews = SparseArray<View>(4)

        private fun showLoadingStatus(status: Int) {
            if (curState == status) return
            curState = status
            var convertView = mStatusViews[status]
            convertView?:let { convertView = mCurStatusView }
            try {
                val view = mAdapter.getView(this, convertView, status)
                if (view != mCurStatusView || mWrapper.indexOfChild(view) < 0) {
                    mCurStatusView?.let { mWrapper.removeView(mCurStatusView) }
                    view.elevation = Float.MAX_VALUE // 设置z轴高度最高保持显示在view最上层
                    mWrapper.addView(view)
                    // 设置loadingView的布局参数为铺满整个wrapper
                    val lp = view.layoutParams
                    lp?.let {
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
                    }
                } else if (mWrapper.indexOfChild(view) != mWrapper.childCount - 1) {
                    // mWrapper还有其它child 把view拉到前台
                    view.bringToFront()
                }
                // 把adapter获取的view缓存到mCurStatusView和mStatusViews中
                mCurStatusView = view
                mStatusViews.put(status, view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * 设置重试任务
         *
         * @param task
         * @return
         */
        fun withRetry(task: (() -> Unit)?): Holder {
            mRetryTask = task
            return this
        }

        /**
         * 设置携带数据
         *
         * @param data
         */
        fun withData(data: Any) {
            this.mData = data
            this
        }

        fun showLoading() = showLoadingStatus(STATUS_LOADING)

        fun showLoadSuccess() = showLoadingStatus(STATUS_LOAD_SUCCESS)

        fun showLoadFailed() = showLoadingStatus(STATUS_LOAD_FAILED)

        fun showEmpty() = showLoadingStatus(STATUS_EMPTY_DATA)
    }
}

interface GAdapter {
    fun getView(holder: Gloading.Holder, convertView: View, status: Int): View
}