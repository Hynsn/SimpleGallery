package com.example.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.SimpleGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.i("TAG", "setContent: ${this.javaClass}")
            SimpleGalleryTheme {
                Log.i("TAG", "SimpleGalleryTheme: ${this.javaClass}")
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Log.i("TAG", "SimpleGalleryTheme: ${this.javaClass}")
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleGalleryTheme {
        Greeting("Android")
    }
}