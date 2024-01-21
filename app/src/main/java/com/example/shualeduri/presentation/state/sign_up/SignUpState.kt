package com.example.shualeduri.presentation.state.sign_up


data class SignUpState(
    val data: Unit? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)