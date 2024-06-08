package screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.astralix.products.Address

@Composable
fun AddressSelection(navController: NavHostController, addressViewModel: AddressViewModel) {
    val addresses by addressViewModel.addresses.collectAsState()
    val selectedAddress = remember { mutableStateOf(Address(street = "qwe", city = "Москва", house = "", flat = "")) }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Адрес доставки",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )

        if (addresses.isEmpty()) {

        } else {
            LazyColumn {
                items(addresses) { address ->
                    AddressItem(
                        address = address,
                        onClick = {
                            selectedAddress.value = address
                            addressViewModel.saveSelectedAddress(address)
                            navController.popBackStack()
                        },
                        onDelete = {
                            addressViewModel.deleteAddress(address)
                        }
                    )
                }
            }
        }


        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Button(
            onClick = { navController.navigate("addAddress") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Добавить новый адрес")
        }
    }
}

@Composable
fun AddressItem(
    address: Address,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${address.street}, ${address.house}, ${address.flat}",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Address")
        }
    }
}

@Composable
fun AddAddressScreen(
    addressViewModel: AddressViewModel,
    onAddressAdded: () -> Unit
) {
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var house by remember { mutableStateOf("") }
    var flat by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Добавить новый адрес",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = street,
            onValueChange = { street = it },
            label = { Text("Улица") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Город") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = house,
            onValueChange = { house = it },
            label = { Text("Дом") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = flat,
            onValueChange = { flat = it },
            label = { Text("Квартира") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                addressViewModel.addAddress(
                    Address(
                        street = street,
                        city = city,
                        house = house,
                        flat = flat
                    )
                )
                onAddressAdded()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Привезти сюда")
        }
    }
}
