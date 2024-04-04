package com.example.astralix.bottomBar

import com.example.astralix.R

sealed class NavigationIteam(var route: String, var icon: Int, var title: String)
{
    data object Home: NavigationIteam("homeScreen", R.drawable.home, "Home")
    data object Search: NavigationIteam("homeSearch", R.drawable.search, "Search")
    data object Favourites: NavigationIteam("favourites", R.drawable.favourites,"Favourites")
    data object Basket: NavigationIteam("basket", R.drawable.basket, "Basket")
    data object Profile:NavigationIteam("profile", R.drawable.profile,"Profile")
}
