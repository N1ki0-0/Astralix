package com.example.astralix.data

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

}