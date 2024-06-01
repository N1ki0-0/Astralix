package com.example.astralix.auth.authEmail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astralix.auth.authGoogle.SignInResult
import com.google.firebase.auth.FirebaseUser
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
):ViewModel() {
    // MutableStateFlow для отслеживания состояния аутентификации пользователя при входе
    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    // MutableStateFlow для отслеживания состояния аутентификации пользователя при регистрации
    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    // MutableStateFlow для отслеживания общего состояния входа/выхода из системы
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    // Получение текущего пользователя из репозитория аутентификации
    val currentUser: FirebaseUser?
        get() = repository.currentUser

    // Инициализация ViewModel: если текущий пользователь уже аутентифицирован, передайте его в loginFlow
    init {
        if(repository.currentUser != null){
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    // Функция для аутентификации пользователя с использованием электронной почты и пароля
    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value =
            Resource.Loading // Установка состояния загрузки перед выполнением аутентификации
        val result = repository.login(email, password) // Вызов репозитория для выполнения аутентификации
        _loginFlow.value = result // Обновление состояния входа с результатом
    }

    // Функция для регистрации нового пользователя с использованием электронной почты и пароля
    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value =
            Resource.Loading // Установка состояния загрузки перед выполнением регистрации
        val result = repository.signup(name, email, password) // Вызов репозитория для выполнения регистрации
        _signupFlow.value = result // Обновление состояния регистрации с результатом
    }

    // Функция для обработки результата аутентификации
    fun onSignInResult(result: SignInResult) {
        // Обновление состояния ViewModel на основе результата аутентификации
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    // Функция для выхода из системы
    fun logout() {
        repository.logout() // Вызов метода выхода из системы в репозитории
        // Сброс всех MutableStateFlow значений и обновление состояния входа/выхода на начальное состояние
        _loginFlow.value = null
        _signupFlow.value = null
        _state.update { SignInState() }
    }
}
