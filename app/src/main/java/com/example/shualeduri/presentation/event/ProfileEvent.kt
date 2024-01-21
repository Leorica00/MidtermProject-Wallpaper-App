package com.example.shualeduri.presentation.event

sealed interface ProfileEvent {
    data object SignOutEvent : ProfileEvent
}