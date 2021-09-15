package com.hynson.learn

fun main(){
    /**
     * 1.{}属于lambda体
     * 2.{}里面有button text 需要让它持有this
     */
    // infax 表达式必须是成对的
    // 模拟compose,，声明式UI
    layout {
        button {

        }
        text {

        }
    }
    /**
     * 优点整理
     * 1.数据添加方便
     * 2.resId 和 数据混合使用方便
     * 3.数据管理统一使用方便
     *
     */
    val builder = build {
        Product("DS03").init {
            val tempTime = TempTime(12,34)
            add(
                Param("土豆",12,mod = "M1",24,tempTime,true),
                Param("烧烤",12,mod = "M2",24,tempTime,true)
            )
        }.printMap()

        Product("DS04")
            .add(
                Param("辣椒",12,mod = "M3",24,TempTime(12,34),true),
                Param("炒肉",12,mod = "M4",24,TempTime(12,34),true)
            )
        Product("DS06")
            .add(
                Param("螺蛳粉",12,mod = "M5",24,TempTime(12,34),true),
                Param("番茄",12,mod = "M6",24,TempTime(12,34),true)
        )
    }
    builder.printMap()
    println(Builder.instance.getNameByMode("M6"))
}