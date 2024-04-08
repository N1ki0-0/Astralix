package com.example.astralix.screens.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController)
{
    Column {
        Text(text = "home")
    }
}