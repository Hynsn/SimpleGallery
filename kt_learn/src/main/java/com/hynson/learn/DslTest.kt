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
     * 2.resId 和 数据混合使用方便，通常业务最好将resID和业务字段分开管理
     * 3.数据管理统一使用方便，会存在如下情况
     * 根据Mode转换对应的Name Icon对象、根据config取出所有预设参数或对应模式的预设参数；
     * 可以减少部分字段需要转换后再使用的工作量，如UI展示使用的bean和业务数据使用的bean未封装在一起时会需要转换，
     * 有时仅用到了bean的部分字段会有同学将bean转换成另外一种bean
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
