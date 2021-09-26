package com.example.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.SimpleGalleryTheme
import com.google.accompanist.glide.rememberGlidePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.i("TAG", "setContent: ${this.javaClass}")
            SimpleGalleryTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Greeting("Anoi")
                        LazyRowItemsDemo()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "popular.title",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(30.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleGalleryTheme {
        Column(
            modifier = Modifier.background(Color(0xFFE0FFE3)),
            horizontalAlignment = Alignment.Start
        ) {
            Greeting("Anoi")
            LazyRowItemsDemo()
        }
    }
}

@Composable
fun LazyRowItemsDemo() {
    val list = mutableListOf<String>()
    for (i in 1..100){
        list.add("项目 $i")
    }
    // 指定宽度、高度
    LazyColumn(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        items(list) {
            Text(text = it,fontSize = TextUnit.Unspecified)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Content() {
    val imageRes = painterResource(id = R.drawable.abc_vector_test)
    Row(
        modifier = Modifier
            .background(Color(0xFFBCE9FF))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageRes,
            null,
            modifier = Modifier
                .padding(8.dp)
                .background(Color(0xFF343434))
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.background(Color(0xFFE0FFE3)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.background(Color(0xFFFFE5E0)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "万俟霜风",
                    style = TextStyle(
                        fontSize = 22.sp
                    ),
                    modifier = Modifier
                        .background(Color(0xFF868FBA))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "@罗德岛",
                    color = Color.Blue,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.background(Color(0xFF96BA86))
                )
            }
            Text(
                text = "ko~ko~da~yo~",
                color = Color(0xff666666),
                modifier = Modifier.background(Color(0x00ffcc)),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
    }
}

