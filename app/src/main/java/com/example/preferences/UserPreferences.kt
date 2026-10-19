package com.example.preferences
data class UserPreferences(
    val name: String = "",
    val password: String = "",
    val remember: Boolean = false
)