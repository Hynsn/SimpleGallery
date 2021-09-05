package com.hynson.learn

class HigherOrderFunc2 {

    fun login(name: String, pwd: String, respResult: (Boolean) -> Unit) {
        if (name == "test" && pwd == "1234")
            respResult(true)
        else
            respResult(false)
    }

    fun show(name: String = "test", action: (String) -> Unit) = action(name)

    // 多lambda
    fun show2(lambda1: (Int) -> Unit, lambda2: (String) -> Unit) {
        lambda1(100)
        lambda2("test")
    }

}

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
    val test1 = HigherOrderFunc2()
    test1.login("test", "1234") {
        println("登录结果 $it")
    }

    test1.show(name = "李四", action = {
        println("版本1 $it")
    })
    // 版本2，编译器提示Lambda argument should be moved out of parentheses 编译器不建议该用法
    test1.show(name = "李四", {
        println("版本2 $it")
    })
    test1.show("李四") {
        println("版本3 $it")
    }
    test1.show {
        println("版本4 $it")
    }
    // 明确指定参数顺序，当参数很多时非常有用
    test1.show2(lambda1 = {
        println("lambda1")
    }, lambda2 = {
        println("lambda2")
    })
    test1.show2(lambda2 = {
        println("lambda2")
    }, lambda1 = {
        println("lambda1")
    })

    // 同样也支持链式，但注意这里的链式调用必须按照函数的实现顺序来，否则编译器会报错
    test1.show2(lambda1 = {
        println("lambda1")
    }) {
        println("lambda2")
    }

    "dd".run {
        println("跑起来了${it.length}")
    }

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

/**
 * 高阶函数+扩展函数
 * fun <万能类型> 万能类型.run( block:万能类型.() -> (万能类型)) = block()
 * 第一个this是被拓展出来的，调用端
 * 第二个this是匿名扩展，lambda里面
 * T.()会给block的lambda会让lambda实现体里面持有this == T本身。
 * T.(T)会给block的lambda会让lambda实现体里面持有it== T本身。
 */
inline fun <T, R> T.run(block: (T) -> (R)): R {
    return block(this)
}
