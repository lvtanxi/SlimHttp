package com.slim.http.intes


/**
 * 简单的一个view接口
 *
 * @author melon
 * @version 1.0
 * @since JDK1.8
 */
interface WidgetInterface {

    /**
     *  根布局
     * @return Int
     */
    fun getRootLayoutId(): Int

    /**
     * 内容布局
     * @return Int
     */
    fun getContentLayoutId(): Int

    /**
     * 根布局View初始化
     * @return Int
     */
    fun initRootView()

    /**
     * 根布局数据初始化
     * @return Int
     */
    fun initRootData()

    /**
     * 页面数据
     */
    fun initData()

    /**
     * 绑定监听
     */
    fun bindListener()

    /**
     * 业务处理
     */
    fun onProcessLogic()

    /**
     * 显示加载
     */
    fun showLoadingView()

    /**
     * 关闭加载
     */
    fun hideLoadingView()

    /**
     * 成功的toast
     */
    fun toastSuccess(message: CharSequence?)

    /**
     * 失败的toast
     */
    fun toastFail(message: CharSequence?)

    /**
     * 显示错误界面
     */
    fun showErrorView(message: CharSequence?)

    /**
     * 显示主界面
     */
    fun showContentView()

}