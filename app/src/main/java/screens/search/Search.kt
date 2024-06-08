package screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.astralix.bottomBar.ProductProfile
import com.example.astralix.products.ProductViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by productViewModel.searchResults.collectAsState(initial = emptyList())
    val searchHistory by productViewModel.searchHistory.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                productViewModel.searchItems(query)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            placeholder = { Text("ПОИСК") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        LazyColumn {


            item {
                Text("Результаты поиска", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
            }
            items(searchResults) { product ->
                Text(
                    text = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("$ProductProfile/${product.id}")
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}