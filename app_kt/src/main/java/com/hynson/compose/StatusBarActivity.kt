package com.hynson.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.statusBarsHeight
import com.hynson.compose.ui.theme.SimpleGalleryTheme

class StatusBarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SimpleGalleryTheme {
                statusTest(window)
            }
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color = Color.White),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                //                Spacer(
//                //                    modifier = Modifier
//                //                        .statusBarsHeight()
//                //                        .fillMaxWidth()
//                //                )
//                CustomToolbar(
//                    leftIcon = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
//                    onLeftIconClick = {
//
//                    },
//                    modifier = Modifier
//                        .height(44.dp)
//                )
//            }
        }
    }
}

@Composable
fun CustomToolbar(
    modifier: Modifier = Modifier,
    title: String = "Toolbar",
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    rightText: String? = null,
    rightTextColor: Color = colorResource(id = R.color.gray),
    onLeftIconClick: () -> Unit = {},
    onRightIconClick: () -> Unit = {},
    titleColor: Color = Color.Black
) {
    Box(modifier = modifier.fillMaxWidth()) {
        if (leftIcon != null) {
            IconButton(
                onClick = onLeftIconClick, modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 52.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = titleColor
        )
        if (rightIcon != null) {
            IconButton(
                onClick = onRightIconClick, modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(imageVector = rightIcon, contentDescription = null)
            }
        } else if (rightText != null) {
            Text(
                text = rightText,
                modifier = Modifier
                    .clickable { onRightIconClick() }
                    .width(52.dp)
                    .wrapContentWidth()
                    .align(Alignment.CenterEnd),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 16.sp,
                color = rightTextColor
            )
        }
    }
}