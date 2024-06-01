package com.example.astralix.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.astralix.R
import com.example.astralix.bottomBar.NavigationIteam
import com.example.astralix.bottomBar.ROUTE_LOGIN
import com.example.astralix.auth.authEmail.AuthViewModel
import com.example.astralix.auth.authGoogle.UserData
import com.example.astralix.ui.theme.Xoli


@Composable
fun Profile(viewModel: AuthViewModel?,
            navController: NavHostController,
            userData: UserData?,
            onSignOut: () -> Unit)
{
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Xoli),
        ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                if(userData?.profilePictureUrl != null){
                    AsyncImage(model = userData.profilePictureUrl, null, modifier = Modifier
                        .padding(end = 5.dp)
                        .size(60.dp)
                        .clip(CircleShape)
                        .clickable {},
                        contentScale = ContentScale.Crop)
                }else{
                Image(painter = painterResource(id = R.drawable.cat_), null, modifier = Modifier
                    .padding(end = 5.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable {},
                    contentScale = ContentScale.Crop)
                }
            }
            Column (modifier = Modifier
                .padding(start = 14.dp)
                .weight(1f)
            ){
                if(userData?.username != null){
                    Text(text = userData.username,
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                else{
                    Text(text = "Name",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.menu_burger),
                    null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                )
            }
        }
        ConstraintLayout (Modifier.padding(start = 32.dp, end = 32.dp, top = 8.dp, bottom = 8.dp)){
            val (topImg,profile,textbonys) = createRefs()

            Image(painterResource(id = R.drawable.kosmos_card), null, modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .constrainAs(profile) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                contentScale = ContentScale.Crop
                )



            Image(painterResource(id = R.drawable.gg), null, modifier = Modifier
                .clickable {}
                .constrainAs(topImg) {
                    end.linkTo(parent.end, 14.dp)
                    top.linkTo(parent.top, 14.dp)
                })
        }

        Text(text = "Мой кабинет",
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 32.dp, top = 8.dp))
        // Мои покупки
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 32.dp, top = 8.dp)
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Row (modifier = Modifier
                .padding(start = 17.dp)

            ){
                Text(text = "мои покупки",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )

                Image(painter = painterResource(id = R.drawable.angle),
                    null,
                    modifier = Modifier
                        .size(18.dp)
                )
            }
        }
        // Избранное
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 32.dp, top = 8.dp)
                .height(45.dp)
                .clickable { navController.navigate(NavigationIteam.Favourites.route) },
            verticalAlignment = Alignment.CenterVertically
        ){
            Row (modifier = Modifier
                .padding(start = 17.dp)

            ){
                Text(text = "избранное",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )

                Image(painter = painterResource(id = R.drawable.angle),
                    null,
                    modifier = Modifier
                        .size(18.dp)
                )
            }

        }
        // Документы
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 32.dp, top = 8.dp)
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Row (modifier = Modifier
                .padding(start = 17.dp)

            ){
                Text(text = "документы",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )

                Image(painter = painterResource(id = R.drawable.angle),
                    null,
                    modifier = Modifier
                        .size(18.dp)
                )
            }
        }
        // Поддержа
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 56.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.podezka), null, modifier = Modifier
                        .padding(start = 16.dp, end = 5.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "служба поддержки",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "+7 (968) 513-20-58",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        // Выйти из профиля
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column (modifier = Modifier
                .padding(start = 17.dp)
                .weight(1f),
            ){
                Text(text = "выйти из профиля",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            IconButton(
                onClick = { viewModel?.logout()
                    onSignOut
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(NavigationIteam.Profile.route) { inclusive = true }
                    } }) {
                Icon(painter = painterResource(id = R.drawable.exit),
                    null,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 5.dp)
                )
                }


        }
    }
}