package com.example.astralix.products

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astralix.db.MainDb
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val mainDb: MainDb
): ViewModel() {
    private val _mainList = mutableStateOf(emptyList<Products>())
    val mainList: MutableState<List<Products>> get() = _mainList

    private val _favoritesList = mutableStateOf(emptyList<Products>())
    val favoritesList: MutableState<List<Products>> get() = _favoritesList

    private val _basketList = MutableStateFlow<List<Products>>(emptyList())
    val basketList: StateFlow<List<Products>> = _basketList

    private val _searchResults = MutableStateFlow<List<Products>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Products?>(null)
    val selectedProduct: StateFlow<Products?> = _selectedProduct

    private var job: Job? = null

    fun getAllItemsByCategory(cat: String) { //Категория
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getAllItemsByCategory(cat).collect { list ->
                _mainList.value = list
            }
        }
    }
    fun getFavorites() { //Избранное
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getFavorites().collect { list ->
                _favoritesList.value = list
            }
        }
    }
    fun getBasket() { //корзина
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getBasket().collect { list ->
                _basketList.value = list
            }
        }
    }
    fun clearBasket() {
        // Clear the basket in the database
        viewModelScope.launch {
            mainDb.dao.clearBasket()
        }
        // Clear the local state
        _basketList.value = emptyList()
    }
    fun insertItem(item: Products) = viewModelScope.launch {// Добавть айтам
        mainDb.dao.insertItem(item)
        getBasket()
    }
    fun searchItems(query: String) { // Поиск
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.searchItemsByTitle(query).collect { list ->
                _searchResults.value = list
            }
            addSearchToHistory(query)
        }
    }
    private fun addSearchToHistory(query: String) { // История поиска
        if (query.isNotBlank() && query !in _searchHistory.value) {
            _searchHistory.value = _searchHistory.value + query
        }
    }
    fun getProductByIdFromDb(id: Int) {
        viewModelScope.launch {
            _selectedProduct.value = mainDb.dao.getProductById(id)
        }
    }
}
