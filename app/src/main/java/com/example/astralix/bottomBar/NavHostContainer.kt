package com.example.astralix.bottomBar

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.astralix.auth.authEmail.AuthViewModel
import com.example.astralix.auth.authGoogle.GoogleAuthUIClient
import com.example.astralix.products.Address
import com.example.astralix.products.LoadingScreen
import com.example.astralix.products.ProductLoaderScreen
import com.example.astralix.products.ProductViewModel
import com.example.astralix.products.Products
import com.example.astralix.products.ProductsProfile
import com.example.astralix.screens.basket.Basket
import com.example.astralix.screens.profile.Profile
import com.example.astralix.screens.search.MainSearch
import screens.profile.LoginScreen
import screens.profile.SignupScreen
import com.example.astralix.products.ProdustHome
import com.example.astralix.screens.Favourites
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import screens.SplashScreen
import screens.profile.Document
import screens.search.AddAddressScreen
import screens.search.AddressSelection
import screens.search.AddressViewModel
import screens.search.SearchScreen

@Composable
fun NavHostContainer (
    viewModel: AuthViewModel,
    productsViewModel: ProductViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
    lifecycleScope: LifecycleCoroutineScope,
    applicationContext: Context,
    navController: NavHostController,
    padding: PaddingValues,
    isShowBottomBar: MutableState<Boolean>
    ) {

    val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    val state by viewModel.state.collectAsStateWithLifecycle()


    NavHost(navController = navController,
        modifier = Modifier.padding(paddingValues = padding),
        startDestination = Splash,
        builder = {
            composable(Splash){
                SplashScreen(navController)
                isShowBottomBar.value = false
            }
            composable(NavigationIteam.Search.route) {
                MainSearch(navController = navController)
                isShowBottomBar.value = true
            }

            composable("addressSelection") {
                AddressSelection(navController, addressViewModel)
                isShowBottomBar.value = true
            }
            composable("addAddress") {
                AddAddressScreen(addressViewModel) {
                    navController.popBackStack()
                }
                isShowBottomBar.value = true
            }

            composable(SearchScreen) {
                SearchScreen(navController)
            }


            composable(NavigationIteam.Basket.route) {
                Basket(navController)
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Profile.route) {
                Profile(viewModel, navController,
                    userData = googleAuthUIClient.getSignedInUser(),
                    onSignOut = {
                        lifecycleScope.launch {
                            googleAuthUIClient.signOut()
                            Toast.makeText(
                                applicationContext,
                                "Signed out",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.popBackStack()
                        }
                    })
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Favourites.route) {
                Favourites(navController)
            }

            composable("$ProductProfile/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                if (productId != null) {
                    ProductLoaderScreen(productId, navController, productsViewModel)
                } else {
                    navController.popBackStack()
                    Toast.makeText(applicationContext, "Invalid product ID", Toast.LENGTH_SHORT).show()
                }
                isShowBottomBar.value = true
            }
            composable("productCategory/{category}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                ProdustHome(productsViewModel, category) { listItem ->
                    navController.navigate("$ProductProfile/${listItem.id}")
                }
                isShowBottomBar.value = true
            }

            //Аунтификация(Вход в профиль)
            composable(ROUTE_LOGIN) {
                LaunchedEffect(key1 = Unit){
                    if(googleAuthUIClient.getSignedInUser() != null)
                    {
                        navController.navigate(NavigationIteam.Profile.route)
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = {result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUIClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if(state.isSignInSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(NavigationIteam.Profile.route)
                    }
                }

                LoginScreen(viewModel, navController, state = state) {
                    lifecycleScope.launch {
                        val signInIntentSender = googleAuthUIClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
                isShowBottomBar.value = false
            }
            //Аунтификация(Регистрация аккаунта)
            composable(ROUTE_SIGNUP) {
                SignupScreen(viewModel, navController)
                isShowBottomBar.value = false
            }
            composable(Document) {
                Document()
                isShowBottomBar.value = true
            }
        }
    )

}