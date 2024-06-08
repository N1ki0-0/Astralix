package com.example.astralix.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.astralix.bottomBar.SearchScreen
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.astralix.R
import com.example.astralix.products.Address
import com.example.astralix.products.ProductViewModel
import com.example.astralix.ui.theme.Xoli
import screens.search.AddressItem
import screens.search.AddressViewModel

@Composable
fun MainSearch(
    navController: NavHostController,
    addressViewModel: AddressViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categories = context.resources.getStringArray(R.array.drawer_list)
    val selectedAddress by addressViewModel.selectedAddress.collectAsState()

    LaunchedEffect(Unit) {
        addressViewModel.loadSelectedAddress()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Xoli)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(SearchScreen)
                }
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = "Search")
                Spacer(modifier = Modifier.width(8.dp))
                Text("ПОИСК", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("addressSelection")}) {
            Text(
                text = selectedAddress?.let { "${it.street}, ${it.house}, ${it.flat}" } ?: "Москва",
                style = MaterialTheme.typography.body1
            )
        }

        categories.forEach { category ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp, top = 6.dp)
                    .height(45.dp)
                    .clickable {
                        navController.navigate("productCategory/$category")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.padding(start = 17.dp)
                ) {
                    Text(
                        text = category,
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.angle),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}

