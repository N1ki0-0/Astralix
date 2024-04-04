package screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astralix.R
import com.example.astralix.ui.theme.Xoli

@Preview
@Composable
fun AuthenticationScreen(){

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column (modifier = Modifier
        .fillMaxSize()
        .background(Xoli)
        .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Column (modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
            ){
            Image(painterResource(id = R.drawable.authentication), null ,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(54.dp),
                contentScale = ContentScale.Crop)
            Text(text = "Authentication", modifier = Modifier
                .padding(top = 12.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold)
        }
        Column (modifier = Modifier
            .fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedTextField(value = email.value,
                onValueChange = { newText -> email.value = newText },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                shape = RoundedCornerShape(size=16.dp),
                label = { Text(text = "Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { newText -> password.value = newText },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ,shape = RoundedCornerShape(size=16.dp),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )


        }

    }
}