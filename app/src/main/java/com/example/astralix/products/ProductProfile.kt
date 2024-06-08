package com.example.astralix.products


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.astralix.R
import com.example.astralix.ui.theme.Xoli
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ProductsProfile(product: Products, productViewModel: ProductViewModel = hiltViewModel()) {
    val buttonText = remember { mutableStateOf("Добавить в корзину") }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Xoli)
                .padding(16.dp)
        ) {
            item {
                ProductImagesCarousel(images = listOf(product.productImage))
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = product.category,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Gray
                )
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${product.price} ₽",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                Text(
                    text = "${(product.price * 1.1).toInt()} ₽", // Assuming original price is 10% higher for discount display
                    style = MaterialTheme.typography.body1,
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "от ${(product.price / 4)} ₽ × 4 платежа",
                    style = MaterialTheme.typography.body2,
                    color = Color(0xFF6200EE),
                    modifier = Modifier.clickable { /* Handle installment info */ }
                )

                Spacer(modifier = Modifier.height(16.dp))
                if(product.volume != "0"){
                Text(
                    text = "Объем / мл",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Gray
                )
                Text(
                    text = product.volume, // Hardcoded volume, adjust accordingly
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))}

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.body1
                )

                Spacer(modifier = Modifier.height(80.dp)) // Spacer for button
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    buttonText.value = "Добавлено в корзину"
                    productViewModel.insertItem(product.copy(isBas = !product.isBas))
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Add to Cart", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(buttonText.value, color = Color.White)
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { productViewModel.insertItem(product.copy(isFav = !product.isFav)) }) {
                Icon(
                    painterResource(id = R.drawable.favourites2),
                    contentDescription = "Favorite",
                    tint = if (product.isFav) Color.Red else Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductImagesCarousel(images: List<String>) {
    val pagerState = rememberPagerState()
    HorizontalPager(
        count = images.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) { page ->
        Image(
            painter = rememberAsyncImagePainter(model = images[page]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
        )
    }
}



@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginScreenPreviewLight() {
    //ProductsProfile(productViewModel = ProductViewModel())
}

