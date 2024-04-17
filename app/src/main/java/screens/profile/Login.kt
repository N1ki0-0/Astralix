package screens.profile

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.astralix.R
import com.example.astralix.bottomBar.NavigationIteam
import com.example.astralix.bottomBar.ROUTE_LOGIN
import com.example.astralix.bottomBar.ROUTE_SIGNUP
import com.example.astralix.auth.AuthViewModel
import com.example.astralix.auth.Resource
import com.example.astralix.data.UserData
import com.example.astralix.ui.theme.Xoli
import com.example.astralix.ui.theme.spacing
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.SignInState
import okhttp3.internal.applyConnectionSpec

@Composable
fun LoginScreen(
    viewModel: AuthViewModel?,
    navController: NavHostController,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginFlow = viewModel?.loginFlow?.collectAsState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Xoli)
    ) {

        val (refHeader, refEmail, refPassword, refButtonLogin, refTextSignup, refLoader, refGoogle) = createRefs()
        val spacing = MaterialTheme.spacing

        Box(
            modifier = Modifier
                .constrainAs(refHeader) {
                    top.linkTo(parent.top, spacing.extraLarge)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .wrapContentSize()
        ) {
            AuthHeader()
        }


        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            modifier = Modifier.constrainAs(refEmail) {
                top.linkTo(refHeader.bottom, spacing.extraLarge)
                start.linkTo(parent.start, spacing.large)
                end.linkTo(parent.end, spacing.large)
                width = Dimension.fillToConstraints
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            modifier = Modifier.constrainAs(refPassword) {
                top.linkTo(refEmail.bottom, spacing.medium)
                start.linkTo(parent.start, spacing.large)
                end.linkTo(parent.end, spacing.large)
                width = Dimension.fillToConstraints
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Button(
            onClick = {
                viewModel?.login(email, password)
            },
            modifier = Modifier.constrainAs(refButtonLogin) {
                top.linkTo(refPassword.bottom, spacing.large)
                start.linkTo(parent.start, spacing.extraLarge)
                end.linkTo(parent.end, spacing.extraLarge)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(text = stringResource(id = R.string.login), style = MaterialTheme.typography.titleMedium)
        }

        IconButton(onClick = onSignInClick,
            modifier = Modifier.constrainAs(refGoogle) {
            top.linkTo(refButtonLogin.bottom, spacing.large)
            start.linkTo(parent.start, spacing.extraLarge)
            end.linkTo(parent.end, spacing.extraLarge)
            width = Dimension.fillToConstraints
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google Icon",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
        }


        Text(
            modifier = Modifier
                .constrainAs(refTextSignup) {
                    top.linkTo(refGoogle.bottom, spacing.medium)
                    start.linkTo(parent.start, spacing.extraLarge)
                    end.linkTo(parent.end, spacing.extraLarge)
                }
                .clickable {
                    navController.navigate(ROUTE_SIGNUP) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                },
            text = stringResource(id = R.string.dont_have_account),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Вход в аккаунт через почту
        loginFlow?.value?.let {
            when(it){
                is Resource.Failure -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }
                Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.constrainAs(refLoader){
                        top.linkTo(parent.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(NavigationIteam.Profile.route) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    }
                }
            }
        }


        //Вход в через гугл
        val context = LocalContext.current
        LaunchedEffect(key1 = state.signInError) {
            state.signInError?.let { error ->
                Toast.makeText(
                    context,
                    error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginScreenPreviewLight() {
    //LoginScreen(null, rememberNavController() )
}

