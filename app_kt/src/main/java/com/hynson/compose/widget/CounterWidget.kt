package com.hynson.compose.widget


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.hynson.compose.MainActivity

/**
 * Author: Hynsonhou
 * Date: 2024/3/21 17:00
 * Description: 小组件
 * History:
 * <author> <time> <version> <desc>
 * Hynsonhou 2024/3/21 1.0 首次创建
 */
private val countPreferenceKey = intPreferencesKey("widget-key")
private val countParamKey = ActionParameters.Key<Int>("widget-key")
/*
* 参考：
* https://juejin.cn/post/7277218424690131005
* https://zhuanlan.zhihu.com/p/530370126
* https://juejin.cn/post/7277799192966692864
* https://blog.csdn.net/u012673089/article/details/136179554
* */

class CounterWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> =
        PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val count = prefs[countPreferenceKey] ?: 1

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically,
                modifier = GlanceModifier
                    .background(color = Color.Yellow)
                    .fillMaxSize()
                    .cornerRadius(50.dp)
            ) {
                Text(
                    text = count.toString(),
                    modifier = GlanceModifier.fillMaxWidth(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = ColorProvider(Color.Blue),
                        fontSize = 50.sp
                    )
                )

                Spacer(modifier = GlanceModifier.padding(8.dp))

                Button(
                    text = "变两倍",
                    modifier = GlanceModifier
                        .background(Color(0xFFB6C0C9))
                        .size(100.dp, 50.dp),
                    onClick = actionRunCallback<UpdateActionCallback>(
                        parameters = actionParametersOf(
                            countParamKey to (count + count)
                        )
                    )
                )
                Button(
                    text = "跳转到首页",
                    modifier = GlanceModifier
                        .background(Color(0xFFB6C0C9))
                        .height(50.dp)
                        .fillMaxWidth(),
                    onClick = actionStartActivity<MainActivity>()
                )
            }
//            val count = remember {
//                mutableStateOf(1)
//            }
//            Column(
//                modifier = GlanceModifier.fillMaxSize()
//                    .background(Color.White),
//                horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
//                verticalAlignment = Alignment.Vertical.CenterVertically
//            ) {
//                Text(text = "First Glance Count: ${count.value}")
//                Image(
//                    provider = ImageProvider(resId = R.color.glance_colorError),
//                    contentDescription = ""
//                )
//                Spacer(modifier = GlanceModifier.height(10.dp))
//                Button(
//                    text = "Count+1", onClick = {
//                        count.value++
//                    }
//                )
//                Spacer(modifier = GlanceModifier.height(10.dp))
//                Button(
//                    text = "Count-1", onClick = {
//                        count.value--
//                    }
//                )
//            }
        }
    }
}


class UpdateActionCallback : ActionCallback {

    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val count = requireNotNull(parameters[countParamKey])

        updateAppWidgetState(
            context = context,
            definition = PreferencesGlanceStateDefinition,
            glanceId = glanceId
        ) { preferences ->
            preferences.toMutablePreferences()
                .apply {
                    this[countPreferenceKey] = count
                }
        }

        CounterWidget().update(context, glanceId)
    }
}
