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

fun layout(action:Layout.() -> Unit){

}

data class TempTime(val temp: Int, val time:Int)

data class Param(val name:String,val resId:Int, val mod: String, val recipe:Int,val tt: TempTime,val shake:Boolean)

class Product(val configMod:String){
    lateinit var hashSets:HashSet<Param>

    init {
        Builder.instance.getMap()[configMod] = this
    }

    fun init(action:Product.() -> Unit): Product{
        action.invoke(this)
        return this
    }

    fun add(vararg params: Param){
        hashSets = params.toHashSet()
    }

    fun printMap(){
        hashSets.forEach {
            println(it)
        }
    }


    override fun toString(): String {
        return "Product(configMod='$configMod')"
    }

}

/**
 * 1.根据config取出所有预设参数
 * 2.根据Mode取出对应的name icon
 *
 *
 */
class Builder(){
    companion object{
        val instance: Builder by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            Builder()
        }
        private val map = HashMap<String,Product>()
    }

    fun getMap() : HashMap<String,Product>{
        return map
    }

    fun getNameByMode(mod: String) : String{
        map.forEach {
            it.value.hashSets.forEach { p ->
                if (p.mod == mod) return p.name
            }
        }
        return ""
    }

    fun printMap(){
        map.forEach {
            println(it.value)
        }
    }
}

fun build(lambda:Builder.() -> Unit) : Builder{
    val builder = Builder()
    lambda.invoke(builder)
    return builder
}