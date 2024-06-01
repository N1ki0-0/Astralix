package com.example.astralix.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.astralix.products.Address
import com.example.astralix.products.Products

@Database(
    entities = [Products::class],
    version = 1
)
abstract class MainDb: RoomDatabase(){
    abstract val dao: Dao
}

@Database(
    entities = [Address::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}