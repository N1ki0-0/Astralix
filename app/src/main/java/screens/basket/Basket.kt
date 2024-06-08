package com.example.astralix.screens.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.astralix.bottomBar.NavigationIteam
import com.example.astralix.products.ProductViewModel
import com.example.astralix.products.Products
import com.example.astralix.ui.theme.Xoli
@Composable
fun Basket(navController: NavHostController, productsViewModel: ProductViewModel = hiltViewModel()) {
    val basketList by productsViewModel.basketList.collectAsState(initial = emptyList())
    var showOrderConfirmation by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Products?>(null) }
    LaunchedEffect(Unit) {
        productsViewModel.getBasket()
    }

    val totalProducts = basketList.size
    val totalPrice = basketList.sumOf { it.price }
    val totalDiscount = basketList.sumOf { it.price * it.discount / 100 }
    val finalPrice = totalPrice - totalDiscount

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Xoli)
                    .padding(16.dp)
            ) {
                // Total summary
                Column {
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
                            text = "$finalPrice ₽",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Checkout button
                Button(
                    onClick = {
                        showOrderConfirmation = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text(text = "оформить заказ", color = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Xoli)
                .padding(innerPadding)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "корзина / $totalProducts шт.", style = MaterialTheme.typography.h5)
                IconButton(onClick = { navController.navigate(NavigationIteam.Profile.route) }) {
                    Icon(Icons.Default.Close, contentDescription = "Назад")
                }
            }

            // Free shipping
            Text(
                text = "бесплатная доставка",
                color = Color.Green,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )

            // Products
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(basketList) { product ->
                    ProductItem(product = product, onProductClick = { selectedProduct = it })
                }
            }
        }

        if (showOrderConfirmation) {
            OrderConfirmationScreen(navController, finalPrice, productsViewModel) {
                showOrderConfirmation = false
            }
        }
    }
}
@Composable
fun ProductItem(product: Products,
                onProductClick: (Products) -> Unit,
                productViewModel: ProductViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onProductClick(product) } // Add clickable to handle product clicks
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
                if (product.discount > 0) {
                    Text(
                        text = "${product.price} ₽",
                        style = MaterialTheme.typography.body2,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${product.price - (product.price * product.discount / 100)} ₽",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F)
                    )
                } else {
                    Text(
                        text = "${product.price} ₽",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        IconButton(onClick = {productViewModel.insertItem(product.copy(isBas = !product.isBas))}) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить")
        }
    }
}


@Composable
fun ProductDetailsDialog(product: Products, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = product.productImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = product.title, style = MaterialTheme.typography.h6)
                Text(text = product.category, style = MaterialTheme.typography.subtitle2)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = product.description, style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    if (product.discount > 0) {
                        Text(
                            text = "${product.price} ₽",
                            style = MaterialTheme.typography.body2,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${product.price - (product.price * product.discount / 100)} ₽",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                    } else {
                        Text(
                            text = "${product.price} ₽",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// Order confirmation screen
@Composable
fun OrderConfirmationScreen(navController: NavHostController,
                            finalPrice: Int,
                            productsViewModel: ProductViewModel = hiltViewModel(),
                            onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
            navController.popBackStack()
        },
        title = { Text("Подтверждение заказа") },
        text = { Text("Ваш заказ был успешно оформлен на сумму $finalPrice ₽") },
        confirmButton = {
            TextButton(
                onClick = {
                    productsViewModel.clearBasket()
                    onDismiss()
                }
            ) {
                Text("ОК")
            }
        }
    )
}