package com.hynson.learn

/**
 * 高阶函数（概念）
 * 函数中有lambda，函数的函数就属于高阶函数
 * java的函数现有输出再有输入，kt的函数先有输入再有输出
 *
 */
class HigherOrderFunc1 {
    // 编译器根据lambda自动推导返回类型
    fun show1(num: Int, lambda: (Int) -> String) = lambda.invoke(num)

    // 定义高阶函数，相当于定义规则，实现交给调用者
    fun show2(n1: Int, n2: Int, n3: Int, lambda: (Int, Int, Int) -> Int) = lambda(n1, n2, n3)

    // 函数的函数
    fun show3(n1: Int, n2: Int): (Int, Int) -> String = { n3, n4 ->
        "show3 :${n1 + n2 + n3 + n4}"
    }
}

fun main() {
    // 声明，用lambda去描述函数的声明

    val methodDefine: (String, Double) -> Any

    // 声明+实现
    var method1: (Int) -> String = {
        it.toString()
    }
    var method2: (Int, Int) -> Unit = { _, n2 -> // _代表拒收
        println("method2 :$n2$")
    }
    var method3 = { num: Int -> print("num:$num") }
    method3 = { print("覆盖后的值:$it") } // 支持覆盖

    // lambda表达式不支持默认值
    fun(str1: String?/* = "default"*/, str2: String) {}

    var method4: (String?, String) -> Unit = { str1, /*default */str2 ->
        print("str1$str1")
    }

    // 给Sring类增加匿名函数，效果我们的lambda体就会持有String本身.
    val method5: String.() -> Unit = {
        println("$this")
    }
    "TEST".method5()
    val method6: Int.(Int) -> String = {
        "两数相加的结果${this + it}"
    }
    println(1.method6(2))// this默认是第一个参数
    println(method6(1, 2))

    val func1 = HigherOrderFunc1()
    val ret = func1.show1(89) {
        "转换结果"
    }
    println("show1 :$ret")

    val ret1 = func1.show2(1, 2, 3) { n1, n2, n3 ->
        n1 + n2 + n3
    }
    val ret2 = func1.show2(1, 2, 3) { n1, n2, n3 ->
        n1 * n2 * n3
    }
    println("show2 :$ret1")
    println("show2 :$ret2")

    // local final匿名函数
    val ret3 = fun(n1: Int, n2: Int): (Int, Int) -> String = { n3, n4 ->
        "ret3 :${n1 + n2 + n3 + n4}"
    }

    // public 静态函数
    val ret4 = func1.show3(1, 2)(3, 4)
    println(ret3(1, 2)(100, 200))
    println(ret4)

    // lambda嵌套
    val ret5: (Int) -> (String) -> (String) -> Int =
        {
            {
                {
                    55
                }
            }
        }
    ret5(4)("dd")("44")
}