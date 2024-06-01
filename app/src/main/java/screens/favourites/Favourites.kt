package com.example.astralix.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.astralix.bottomBar.ProductProfile
import com.example.astralix.products.ProductViewModel
import com.example.astralix.products.TwoProductsRow
import com.example.astralix.ui.theme.Xoli

@Composable
fun Favourites(navController: NavHostController, productsViewModel: ProductViewModel = hiltViewModel()) {
    val favoritesList by productsViewModel.favoritesList

    LaunchedEffect(Unit) {
        productsViewModel.getFavorites()
    }

    Column(modifier = Modifier.fillMaxSize().background(Xoli)) {
        Text(
            text = "Избранное",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp, start = 16.dp)
        )

        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(favoritesList.chunked(2)) { rowItems ->
                TwoProductsRow(
                    firstItem = rowItems[0],
                    secondItem = rowItems.getOrNull(1),
                    onClick = { item ->
                        navController.navigate("$ProductProfile/${item.id}")
                    }
                )
            }
        }
    }
}





