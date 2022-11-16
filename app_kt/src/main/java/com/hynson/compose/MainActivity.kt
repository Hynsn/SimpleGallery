package com.hynson.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //    Image(
            //        painter = painterResource(id = R.drawable.ic_launcher_background),
            //        contentDescription = "popular.title",
            //        contentScale = ContentScale.Crop,
            //        modifier = Modifier
            //            .padding(8.dp)
            //            .size(30.dp)
            //            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            //    )
            //            Log.i("TAG", "setContent: ${this.javaClass}")
            //            SimpleGalleryTheme {
            //
            //            }
            SimpleList(title = stringResource(id = R.string.device_settings), onBack = {
                finish()
            })
        }
    }
}
