package com.example.shualeduri.presentation.event

sealed interface SplashEvent {
    data object ReadSessionEvent : SplashEvent
}