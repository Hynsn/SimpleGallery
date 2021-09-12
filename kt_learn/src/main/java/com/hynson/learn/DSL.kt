package com.hynson.learn

class Button(){

}
class Text(){

}

class Layout{
    fun button(action: Button.() -> Unit){

    }
    fun text(action: Text.() -> Unit){

    }
}

data class TempTime(val temp: Int, val time:Int)

class Param(val name:String,val resId:Int, val mod: String, val recipe:Int,val tt: TempTime,val shake:Boolean){

    override fun toString(): String {
        return "Param(name='$name')"
    }
}

class Product(val configMod:String){
    private lateinit var pparams:List<Param>

    companion object {
        val map = HashMap<String,Product>()
    }

    init {

    }

    fun init(action:Product.() -> Unit): Product{
        action.invoke(this)
        return this
    }

    fun add(vararg params: Param){
        pparams = params.toList()

        map[configMod] = this
    }

    override fun toString(): String {
        return "Product(configMod='$configMod')"
    }

    fun printMap(){
        map.forEach {
            println(it.value.pparams)
        }
    }

}

/**
 * 1.根据config取出所有预设参数
 * 2.根据Mode取出对应的name icon
 *
 *
 */
class Builder(){
    fun get(key:String):String?{
//        map[configMod] = configMod
//        return map.get(key)

        return null
    }


}

fun build(lambda:Builder.() -> Unit) : Builder{
    val builder = Builder()
    lambda.invoke(builder)
    return builder
}

fun layout(action:Layout.() -> Unit){

}

/**
 * 1.{}属于lambda体
 * 2.{}里面有button text 需要让它持有this
 */

/**
 * 优点整理
 * 1.数据添加方便
 * 2.resId 和 数据混合使用方便
 * 3.数据管理统一使用方便
 *
  */

fun main(){
    // 模拟compose,，声明式UI
    layout {
        button {

        }
        text {

        }
    }
    // infax 表达式必须是成对的


    /*
    协程解决的问题，使用同步思维达到异步的效果


    串行思维

    解决接口过多回调嵌套的情况

    suspend 挂起函数，可能会执行异常操作调用需要传递，





协程原理
3个点
首次执行传入续体和上下文
with resume

挂起枚举状态

调用机传递，最终会调用invoke suspend 以此类推最后跳出循环。

suspend就是一个提醒功能，需要传递
怎么让多个协程同时执行，最后再一起执行？
async{}.await()
     */

    val builder = build {
        Product("DS03").init {
            val tempTime = TempTime(12,34)
            add(
                Param("test",12,mod = "M1",24,tempTime,true),
                Param("test",12,mod = "M2",24,tempTime,true)
            )
        }.printMap()

        Product("DS04")
            .add(
                Param("test",12,mod = "M3",24,TempTime(12,34),true),
                Param("test",12,mod = "M4",24,TempTime(12,34),true)
            )
        Product("DS06")
            .add(
                Param("test",12,mod = "M5",24,TempTime(12,34),true),
                Param("test",12,mod = "M6",24,TempTime(12,34),true)
        )
    }
}