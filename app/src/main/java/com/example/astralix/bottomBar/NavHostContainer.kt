package com.example.astralix.bottomBar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.astralix.auth.AuthViewModel
import com.example.astralix.screens.basket.Basket
import com.example.astralix.screens.mainscreen.MainScreen
import com.example.astralix.screens.profile.Profile
import com.example.astralix.screens.search.MainSearch
import screens.mainscreen.LoginScreen
import screens.mainscreen.SignupScreen
import screens.profile.AuthenticationScreen

@Composable
fun NavHostContainer(
    viewModel: AuthViewModel,
    navController: NavHostController,
    padding: PaddingValues,
    isShowBottomBar: MutableState<Boolean>
    ) {
    NavHost(navController = navController,
        modifier = Modifier.padding(paddingValues = padding),
        startDestination = ROUTE_LOGIN,
        builder = {
            composable(NavigationIteam.Home.route) {
                MainScreen(navController)
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Search.route) {
                MainSearch(navController)
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Basket.route) {
                Basket(navController)
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Profile.route) {
                Profile(viewModel, navController)
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Favourites.route) {
                AuthenticationScreen()
                isShowBottomBar.value = true
            }
            //Аунтификация
            composable(ROUTE_LOGIN) {
                LoginScreen(viewModel, navController)
                isShowBottomBar.value = false
            }
            composable(ROUTE_SIGNUP) {
                SignupScreen(viewModel, navController)
                isShowBottomBar.value = false
            }
        })
}