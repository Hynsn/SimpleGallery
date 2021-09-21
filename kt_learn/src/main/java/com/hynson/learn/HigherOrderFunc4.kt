package com.hynson.learn

class ClickListener<T> {
    // 执行动作
    private val actions = arrayListOf<(T?) -> Unit>()

    // 执行数据
    private val resIds = arrayListOf<(T?)>()

    /**
     * 添加点击事件
     */
    fun addListener(id:T?, action:(T?) -> Unit){
        val index = resIds.indexOf(id)
        if(index == -1){
            actions += action
            resIds += id
        }
        else {
            actions[index] = action
            println("View $id 重复添加事件...")
        }
    }

    /**
     * 模拟点击事件
     */
    fun touchListeners() {
        if(actions.isEmpty()) return
        actions.forEachIndexed { index, item ->
            item.invoke(resIds[index])
        }
    }
}

fun main() {
    val listener = ClickListener<Int>()
    listener.addListener(1){
        println("点击了$it")
    }
    listener.addListener(2) {
        println("点击了$it 方式1 ")
    }
    // 函数引用
    listener.addListener(2,::handle)
    listener.addListener(3,::handle)

    listener.touchListeners()
}

fun <T> handle(id: T?){
    println("点击了$id 方式2")
}