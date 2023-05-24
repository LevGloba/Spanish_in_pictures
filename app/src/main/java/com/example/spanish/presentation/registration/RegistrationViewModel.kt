package com.example.spanish.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spanish.di.SingltonObject
import com.example.spanish.di.isEmailValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class RegistrationStatus{
    data class Error(val e: String): RegistrationStatus()
    object Return: RegistrationStatus()
}

class RegistrationViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(RegistrationStatus())
    val uiState: StateFlow<RegistrationStatus> = _uiState.asStateFlow()

    fun createUserWithEmail(
        email: String,
        password: String,
        confirmPassword: String,
        lastName: String,
        firstName: String,
        emailTeacher: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!email.isEmailValid() || !emailTeacher.isEmailValid())
                _uiState.emit(RegistrationStatus.Error("Email is Invalid!"))
            else if (password.isEmpty())
                _uiState.emit(RegistrationStatus.Error("Password is Empty!"))
            else if (confirmPassword != password)
                _uiState.emit(RegistrationStatus.Error( "ConfirmPassword is't matching password!"))
            else if (lastName.isEmpty())
                _uiState.emit(RegistrationStatus.Error( "Last name is Empty!"))
            else if (firstName.isEmpty())
                _uiState.emit(RegistrationStatus.Error("Last name is Empty!"))
            else {
                try {
                    SingltonObject.auth.createUserWithEmailAndPassword(email, password)
                    _uiState.emit(RegistrationStatus.Return)
                }catch (e: Throwable) {
                    _uiState.emit(RegistrationStatus.Error(e.message?: "Error"))
                }
            }
        }
    }

}