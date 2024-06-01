package com.example.astralix.screens.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.astralix.products.ProductViewModel
import com.example.astralix.products.Products
import com.example.astralix.ui.theme.Xoli


@Composable
fun Basket(navController: NavHostController, productsViewModel: ProductViewModel = hiltViewModel()) {
    val totalProducts = 2//products.size
    val totalPrice = 18900//products.sumBy { it.price * it.quantity }
    val totalDiscount = 20//products.sumBy { it.discount }

    val basketList by productsViewModel.favoritesList
    LaunchedEffect(Unit) {
        productsViewModel.getFavorites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Xoli)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "корзина / $totalProducts шт.", style = MaterialTheme.typography.h5)
            IconButton(onClick = { /* handle delete all */ }) {
                Icon(Icons.Default.Close, contentDescription = "Удалить всё")
            }
        }

        // Free shipping
        Text(
            text = "бесплатная доставка",
            color = Color.Green,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Products
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(basketList){list ->
                ProductItem(list)
            }
        }

        // Total summary
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Text(
                text = "сумма заказа",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "стоимость продуктов", style = MaterialTheme.typography.body2)
                Text(text = "$totalPrice ₽", style = MaterialTheme.typography.body2)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "скидка", style = MaterialTheme.typography.body2)
                Text(text = "- $totalDiscount ₽", style = MaterialTheme.typography.body2)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "итого",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${totalPrice - totalDiscount} ₽",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Checkout button
        Button(
            onClick = { /* handle checkout */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.Black),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = "оформить заказ", color = Color.White)
        }
    }
}

@Composable
fun ProductItem(product: Products, productsViewModel: ProductViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = product.productImage),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.title, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = product.category, style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
//                if (product.discount > 0) {
//                    Text(
//                        text = "${product.price} ₽",
//                        style = MaterialTheme.typography.body2,
//                        textDecoration = TextDecoration.LineThrough
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "${product.price - product.discount} ₽",
//                        style = MaterialTheme.typography.body1,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFFD32F2F)
//                    )
//                } else {
//                    Text(
//                        text = "${product.price} ₽",
//                        style = MaterialTheme.typography.body1,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
            }
        }
    }
}