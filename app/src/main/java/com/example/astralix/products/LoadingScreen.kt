package com.example.astralix.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController


@Composable
fun ProductLoaderScreen(productId: Int, navController: NavHostController, productsViewModel: ProductViewModel) {
    val context = LocalContext.current
    val selectedProduct by productsViewModel.selectedProduct.collectAsState()

    LaunchedEffect(productId) {
        productsViewModel.getProductByIdFromDb(productId)
    }

    if (selectedProduct == null) {
        LoadingScreen()
    } else {
        ProductsProfile(product = selectedProduct!!)
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}