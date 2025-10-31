package com.example.week78soal1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.week78soal1.ui.theme.Week78Soal1Theme
import com.example.week78soal1.ui.view.viewWeather

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week78Soal1Theme {
                viewWeather()
            }
        }
    }
}
