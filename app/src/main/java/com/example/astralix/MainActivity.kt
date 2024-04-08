package com.example.astralix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.astralix.bottomBar.NavigationIteam
import com.example.astralix.ui.theme.AstralixTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.astralix.bottomBar.NavHostContainer
import com.example.astralix.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val isShowBottomBar = remember { mutableStateOf(false) }
            Scaffold(
                bottomBar = {
                    if (isShowBottomBar.value)
                        BottomNavigationBar(navController)
                            },
                content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
                    NavHostContainer(
                        viewModel,
                        isShowBottomBar = isShowBottomBar,
                        navController = navController,
                        padding = padding)
                },
                backgroundColor = colorResource(R.color.white) // Set background color to avoid the white flashing when you switch between screens
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationIteam.Search,
        NavigationIteam.Favourites,
        NavigationIteam.Home,
        NavigationIteam.Profile,
        NavigationIteam.Basket
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.black),
        contentColor = Color.White
    )
    {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach{items ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = items.icon), contentDescription = items.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == items.route,
                onClick = { navController.navigate(items.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true }
                }
            )
        }
    }
}

//
@Composable
fun TopBar(

) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.White
    )
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AstralixTheme {

    }
}