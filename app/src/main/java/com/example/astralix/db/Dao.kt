package com.example.astralix.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.astralix.products.Address
import com.example.astralix.products.Products
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Products)
    @Delete
    suspend fun deleteItem(item: Products)

    @Query("SELECT * FROM productItem WHERE category LIKE :cat")
    fun getAllItemsByCategory(cat: String): Flow<List<Products>>
    @Query("SELECT * FROM productItem WHERE isFav = 1")
    fun getFavorites(): Flow<List<Products>>

    @Query("SELECT * FROM productItem WHERE isBas = 1")
    fun getBasket(): Flow<List<Products>>

    @Query("UPDATE productItem SET isBas = 0 WHERE isBas = 1")
    suspend fun clearBasket()
    @Query("SELECT * FROM productItem WHERE id = :id")
    suspend fun getProductById(id: Int): Products?
    @Query("SELECT * FROM productItem WHERE title LIKE '%' || :searchQuery || '%'")
    fun searchItemsByTitle(searchQuery: String): Flow<List<Products>>
}

@Dao
interface AddressDao {

    @Insert
    suspend fun insertAddress(address: Address)
    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("SELECT * FROM addresses")
    fun getAllAddresses(): Flow<List<Address>>

    @Query("SELECT * FROM addresses WHERE id = :id LIMIT 1")
    suspend fun getAddressById(id: Int): Address?
}