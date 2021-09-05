package com.hynson.learn

class View{
    /**
     * 1.{}属于lambda体
     * 2.{}里面有button text 需要让它持有this
     */
    fun button(action: View.() -> Unit){

    }
    fun text(action: View.() -> Unit){

    }
}
fun layout(action:View.() -> Unit){

}

fun main(){
    // 模拟compose,，声明式UI
    layout {
        button {

        }
        text {

        }
    }
    // infax 表达式必须是成对的
}