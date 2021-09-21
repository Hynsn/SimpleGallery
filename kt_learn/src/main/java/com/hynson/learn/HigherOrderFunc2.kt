package com.hynson.learn

fun main() {

    // 默认Unit
    fun t01(){ 1}
    fun t02(){ 7.5f}
    fun t03(){ true}

    fun t04():String { return "test"} // 默认String，在函数体内部,return xxx编译器是不支持类型推导的

    fun s01() = { } // () ->Unit，函数中又是函数

    fun s02() = run{ println()} // 由里面向外面决定返回类型 run函数返回类型为泛型，返回Unit类型
    fun s03() : Boolean = run { true }
    fun s04() : (Int) -> Boolean = { true } // 输入(int) -> 输出Boolean

    s03()
    s04()(4)

    fun s05() = { n1:Int ->
        println("$n1")
        true
    }
    s05()(34)

    // 开发时一般不会用fun关键字 + 声明处，lambda+函数==高阶函数
    fun s06(){

    }
    val s07 = {

    }
    // 区别：s06是一个函数，s07接收的是一个匿名函数的变量，它可以执行这个匿名函数

}
/*
// 扩展函数，你对谁扩展 this==xxx本身
// 默认public static
fun Glide.show(){
    // this == Glide
}
fun OkHttp.showInfo(){
}
*/