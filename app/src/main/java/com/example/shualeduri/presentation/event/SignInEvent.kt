package com.example.shualeduri.presentation.event

sealed interface SignInEvent {
    data class SignIn(val email: String, val password: String) : SignInEvent
    data object GoToSignUpFragmentEvent : SignInEvent
}

