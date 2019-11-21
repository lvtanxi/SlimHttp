package com.slim.http.intes

/**
 *分页数据
 *
 * @author melon
 * @version 1.0
 * @since JDK1.8
 */
interface PageWidgetInterface {

    /**
     * 显示空布局
     * @param isSuccess Boolean 是否成功
     */
    fun showEmptyView(isSuccess: Boolean)

    /**
     * 加载数据
     */
    fun loadData()

    /**
     * 停止刷新
     * @param isSuccess Boolean
     */
    fun stopRefresh(isSuccess: Boolean)

    /**
     * 添加数据
     * @param items Any?
     */
    fun addItems(items: Any?)

    /**
     * 清除加载更多监听
     */
    fun clearLoadMoreListener()

    /**
     *  分页条件
     * @return Map<String, String>
     */
    fun getPageMap(): Map<String, String>

}