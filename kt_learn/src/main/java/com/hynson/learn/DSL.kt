package com.hynson.learn

class View{


}

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

class Param(name:String, resId:Int, mod: String, recipe:Int, tt: TempTime, shake:Boolean){

}

class Product(val configMod:String){

    companion object {
        val map = HashMap<String,Product>()

        val productMap = HashMap<String,Param>()
    }

    init {
        map.put(configMod,this)
    }

    fun add(action:Param.() -> Unit){
        action.let {
            println("add-->$it")
        }
        map.forEach {
            println("it-->${it.value} ${System.identityHashCode(map)}")
        }
    }
    fun add2(param: Param): Product{
        productMap.put(configMod,param)

        return this
    }

    override fun toString(): String {
        return "Product(configMod='$configMod')"
    }

}

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

    val builder = build {
        Product("DS03").add {
            Param("test",12,mod = "测试",24,TempTime(12,34),true)
            Param("test",12,mod = "测试",24,TempTime(12,34),true)
        }
        Product("DS04")
            .add2(Param("test",12,mod = "测试",24,TempTime(12,34),true))
            .add2(Param("test",12,mod = "测试",24,TempTime(12,34),true))
        Product("DS06").add {
            Param("test",12,mod = "测试",24,TempTime(12,34),true)
            Param("test",12,mod = "测试",24,TempTime(12,34),true)
        }
    }
}