package com.example.astralix.products

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astralix.db.MainDb
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var job: Job? = null


    fun getAllItemsByCategory(cat: String) {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getAllItemsByCategory(cat).collect { list ->
                _mainList.value = list
            }
        }
    }

    fun getFavorites() {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getFavorites().collect { list ->
                _favoritesList.value = list
            }
        }
    }

    fun insertItem(item: Products) = viewModelScope.launch {
        mainDb.dao.insertItem(item)
    }

    fun deleteItem(item: Products) = viewModelScope.launch {
        mainDb.dao.deleteItem(item)
    }

    fun getProductById(id: Int?): Products? {
        return _mainList.value.find { it.id == id } ?: _favoritesList.value.find { it.id == id }
    }
}
