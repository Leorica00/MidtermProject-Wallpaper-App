package com.example.shualeduri.presentation.event

sealed interface SignUpEvent {
    data class SignUp(val email: String, val password: String, val repeatPassword: String, val fullName: String) : SignUpEvent
    data object GoToSignInFragmentEvent : SignUpEvent
}
