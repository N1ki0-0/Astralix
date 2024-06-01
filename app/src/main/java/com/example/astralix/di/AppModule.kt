package com.example.astralix.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.astralix.auth.authEmail.AuthRepository
import com.example.astralix.auth.authEmail.AuthRepositoryImpl
import com.example.astralix.db.AddressDao
import com.example.astralix.db.AppDatabase
import com.example.astralix.db.MainDb
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun povideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance() //аунтификация
    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl //товар

    @Provides
    fun provideAddressDao(db: AppDatabase): AddressDao = db.addressDao() // адреса доставки
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences { // адреса доставки
        return app.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb{
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "info.db"
        ).createFromAsset("db/info.db").build()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database.db"
        ).fallbackToDestructiveMigration().build()
    }

}