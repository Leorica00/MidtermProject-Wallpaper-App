package com.example.shualeduri.presentation.screen.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shualeduri.domain.usecase.validation.EmailValidatorUseCase
import com.example.shualeduri.data.common.Resource
import com.example.shualeduri.domain.usecase.sign_up.SignUpWithEmailAndPasswordUseCase
import com.example.shualeduri.domain.usecase.sign_up.UpdateDisplayNameUseCase
import com.example.shualeduri.domain.usecase.validation.FullNameValidationUseCase
import com.example.shualeduri.domain.usecase.validation.RepeatPasswordValidatorUseCase
import com.example.shualeduri.presentation.event.SignUpEvent
import com.example.shualeduri.presentation.state.sign_up.SignUpState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpWithEmailAndPasswordUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: RepeatPasswordValidatorUseCase,
    private val fullNameValidator: FullNameValidationUseCase,
    private val repeatPasswordValidator: RepeatPasswordValidatorUseCase,
    private val updateDisplayNameUseCase: UpdateDisplayNameUseCase
) : ViewModel() {
    private val _signUpStateFlow = MutableStateFlow(SignUpState())
    val signUpStateFlow get() = _signUpStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SignUpUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> validateForm(
                email = event.email,
                password = event.password,
                repeatPassword = event.repeatPassword,
                fullName = event.fullName
            )

            is SignUpEvent.GoToSignInFragmentEvent -> viewModelScope.launch {
                _uiEvent.emit(
                    SignUpUiEvent.NavigateToSignIn
                )
            }
        }
    }

    private fun signUpWithEmailAndPassword(email: String, password: String, fullName: String) =
        viewModelScope.launch {
            signUpUseCase(email, password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _signUpStateFlow.update { currentState ->
                        currentState.copy(
                            isLoading = resource.loading
                        )
                    }

                    is Resource.Success -> {
                        _signUpStateFlow.update { currentState -> currentState.copy(data = resource.response) }
                        if (Firebase.auth.currentUser != null) {
                            updateDisplayNameUseCase(fullName = fullName).collect {
                                when (it) {
                                    is Resource.Loading -> _signUpStateFlow.update { currentState ->
                                        currentState.copy(
                                            isLoading = it.loading
                                        )
                                    }

                                    is Resource.Success -> _uiEvent.emit(SignUpUiEvent.NavigateToWallpapers)
                                    is Resource.Error -> _signUpStateFlow.update { currentState ->
                                        currentState.copy(
                                            errorMessage = it.errorMessage
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is Resource.Error -> _signUpStateFlow.update { currentState ->
                        currentState.copy(
                            errorMessage = resource.errorMessage
                        )
                    }
                }
            }
        }

    private fun validateForm(
        email: String,
        password: String,
        repeatPassword: String,
        fullName: String
    ) {
        if (!fullNameValidator(fullName)) {
            _signUpStateFlow.update { it.copy(errorMessage = "Enter your full name") }
        } else if (!emailValidator(email)) {
            _signUpStateFlow.update { it.copy(errorMessage = "Email is not valid!") }
        } else if (!passwordValidator(password, repeatPassword)) {
            _signUpStateFlow.update { it.copy(errorMessage = "Password is not valid!") }
        } else if (!repeatPasswordValidator(password, repeatPassword)) {
            _signUpStateFlow.update { it.copy(errorMessage = "Repeat password correctly") }
        } else {
            signUpWithEmailAndPassword(email = email, password = password, fullName = fullName)
        }
    }

    sealed interface SignUpUiEvent {
        data object NavigateToWallpapers : SignUpUiEvent
        data object NavigateToSignIn : SignUpUiEvent
    }
}