package com.example.astralix.products

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "productItem")
data class Products(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String,
    val price: Int,
    val discount: Int,
    val volume: String,
    val productImage: String,
    val category: String,
    val isFav: Boolean,
    val isBas: Boolean
)

@Entity(tableName = "addresses")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val street: String,
    val city: String,
    val house: String,
    val flat: String
)
