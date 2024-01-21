package com.example.shualeduri.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shualeduri.domain.usecase.profile.SignOutUseCase
import com.example.shualeduri.domain.usecase.splash.GetUserUseCase
import com.example.shualeduri.presentation.event.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(getUserUseCase: GetUserUseCase, private val signOutUseCase: SignOutUseCase): ViewModel() {
    private val _userStateFlow = MutableStateFlow(getUserUseCase())
    val userStateFlow = _userStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.SignOutEvent -> signOut()
        }
    }

    private fun signOut() {
        signOutUseCase()
        viewModelScope.launch {
            _uiEvent.emit(ProfileUiEvent.GoToSignInFragmentEvent)
        }
    }

    sealed interface ProfileUiEvent {
        data object GoToSignInFragmentEvent : ProfileUiEvent
    }
}