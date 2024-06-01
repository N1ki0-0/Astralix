package com.example.astralix.products

import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.astralix.R
import com.example.astralix.bottomBar.ProductProfile
import com.example.astralix.ui.theme.Gray
import com.example.astralix.ui.theme.Red
import com.example.astralix.ui.theme.Xol
import com.example.astralix.ui.theme.Xoli


@Composable
fun ProdustHome(productViewModel: ProductViewModel, titleProduct: String, onClick: (Products) -> Unit) {
    LaunchedEffect(titleProduct) {
        productViewModel.getAllItemsByCategory(titleProduct)
    }

    val mainList by productViewModel.mainList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Xoli)
    ) {


        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(mainList.chunked(2)) { rowItems ->
                TwoProductsRow(
                    firstItem = rowItems[0],
                    secondItem = rowItems.getOrNull(1),
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun TwoProductsRow(firstItem: Products, secondItem: Products?, onClick: (Products) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProductItem(item = firstItem, onClick = onClick, modifier = Modifier.weight(1f))
        secondItem?.let { ProductItem(item = it, onClick = onClick, modifier = Modifier.weight(1f)) }
    }
}

@Composable
fun ProductItem(item: Products,
                onClick: (Products) -> Unit,
                modifier: Modifier = Modifier,
                mainViewModel: ProductViewModel = hiltViewModel()) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick(item)
            },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Xol)
        ) {
            Box() {
                ProductImage(item.productImage) // Расширяем изображение на всю доступную площадь
                IconButton(
                    onClick = { mainViewModel.insertItem(item.copy(isFav = !item.isFav)) },
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(painterResource(id = R.drawable.favourites2), tint = if(item.isFav) Red else Gray, contentDescription = "Favorite")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.title, style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${item.price} ₽",
                style = MaterialTheme.typography.body1
                    .copy(fontWeight = FontWeight.Bold, fontSize = 20.sp), // Увеличим размер и сделаем текст жирным
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}



@Composable
private fun ProductImage(itemImage: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(itemImage)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(140.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

