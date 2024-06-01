package com.example.astralix.auth.authGoogle

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.astralix.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException


class GoogleAuthUIClient (
    private val context: Context,
    private val oneTapClient: SignInClient
){
    // Получение экземпляра аутентификации Firebase
    private val auth = Firebase.auth

    // Функция асинхронного выполнения входа в аккаунт Google
    suspend fun signIn(): IntentSender? {
        // Попытка начала процесса аутентификации с помощью Google One Tap API
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e: Exception) {
            // Обработка исключений, возникающих при попытке входа
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        // Возвращение объекта IntentSender для запуска процесса аутентификации
        return result?.pendingIntent?.intentSender
    }

    // Функция асинхронного выполнения входа с использованием полученного Intent
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        // Получение учетных данных из Intent с помощью Google One Tap API
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            // Аутентификация пользователя с использованием учетных данных Google
            val user = auth.signInWithCredential(googleCredentials).await().user
            // Возвращение результатов аутентификации
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch(e: Exception) {
            // Обработка исключений, возникающих при попытке аутентификации
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    // Функция асинхронного выполнения выхода из аккаунта
    suspend fun signOut() {
        try {
            // Выход из аккаунта с использованием Google One Tap API
            oneTapClient.signOut().await()
            // Выход из аккаунта Firebase
            auth.signOut()
        } catch(e: Exception) {
            // Обработка исключений, возникающих при попытке выхода из аккаунта
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    // Функция получения данных о текущем авторизованном пользователе
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        // Получение данных пользователя Firebase
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    // Функция построения запроса на начало процесса аутентификации
    private fun buildSignInRequest(): BeginSignInRequest {
        // Конфигурация параметров запроса на аутентификацию с помощью Google One Tap API
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.wed_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}
