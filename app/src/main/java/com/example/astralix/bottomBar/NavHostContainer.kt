package com.example.astralix.bottomBar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.astralix.bottomBar.NavigationIteam
import com.example.astralix.screens.Favourites
import com.example.astralix.screens.basket.Basket
import com.example.astralix.screens.mainscreen.MainScreen
import com.example.astralix.screens.profile.Profile
import com.example.astralix.screens.search.MainSearch
import screens.profile.AuthenticationScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    isShowBottomBar: MutableState<Boolean>
    ) {
    NavHost(navController = navController,
        modifier = Modifier.padding(paddingValues = padding),
        startDestination = NavigationIteam.Home.route,
        builder = {
            composable(NavigationIteam.Home.route) {
                MainScreen()
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Search.route) {
                MainSearch()
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Basket.route) {
                Basket()
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Profile.route) {
                Profile()
                isShowBottomBar.value = true
            }
            composable(NavigationIteam.Favourites.route) {
                AuthenticationScreen()
                isShowBottomBar.value = true
            }
        })
}