package screens.search

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.example.astralix.db.AddressDao
import com.example.astralix.products.Address
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressDao: AddressDao,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _addresses = MutableStateFlow<List<Address>>(emptyList())
    val addresses: StateFlow<List<Address>> get() = _addresses

    private val _selectedAddress = MutableStateFlow<Address?>(null)
    val selectedAddress: StateFlow<Address?> get() = _selectedAddress

    init {
        viewModelScope.launch {
            addressDao.getAllAddresses().collect { addressList ->
                _addresses.value = addressList
            }
            // Загрузите сохраненный адрес при инициализации
            loadSelectedAddress()
        }
    }

    fun addAddress(address: Address) {
        viewModelScope.launch {
            try {
                addressDao.insertAddress(address)
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error inserting address", e)
            }
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            try {
                addressDao.deleteAddress(address)
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error deleting address", e)
            }
        }
    }

    fun saveSelectedAddress(address: Address) {
        viewModelScope.launch {
            try {
                _selectedAddress.value = address
                sharedPreferences.edit().putInt("selected_address_id", address.id ?: -1).apply()
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error saving selected address", e)
            }
        }
    }

    fun loadSelectedAddress() {
        viewModelScope.launch {
            try {
                val selectedAddressId = sharedPreferences.getInt("selected_address_id", -1)
                if (selectedAddressId != -1) {
                    val savedAddress = addressDao.getAddressById(selectedAddressId)
                    _selectedAddress.value = savedAddress
                }
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error loading selected address", e)
            }
        }
    }
}