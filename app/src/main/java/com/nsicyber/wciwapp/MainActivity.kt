package com.nsicyber.wciwapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.nsicyber.wciwapp.presentation.navigation.NavigationGraph
import com.nsicyber.wciwapp.ui.theme.WhatCanIWatchAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatCanIWatchAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavigationGraph(modifier = Modifier)
                }
            }
        }
    }
}
