package com.example.spanish.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spanish.di.SingltonObject
import com.example.spanish.di.isEmailValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class AuthStatus{
    data class Error(val e: String): AuthStatus()
    object NextStep: AuthStatus()
}
class AuthViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(AuthStatus())
    val uiState: StateFlow<AuthStatus> = _uiState.asStateFlow()

    fun signInUserWithEmail(
        email: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!email.isEmailValid())
                _uiState.emit(AuthStatus.Error("Email is Invalid!"))
            else if (password.isEmpty())
                _uiState.emit( AuthStatus.Error("Password is Empty!"))
            else {
                try {
                    val result = SingltonObject.auth.signInWithEmailAndPassword(email, password).await()
                    if (result.user?.isAnonymous == true)
                        _uiState.emit( AuthStatus.Error( "Вы не зарегестрированны"))
                    else
                        _uiState.emit(AuthStatus.NextStep)
                }catch (e: Throwable) {
                    _uiState.emit( AuthStatus.Error(e.message?: "Error"))
                }
            }
        }
    }
}