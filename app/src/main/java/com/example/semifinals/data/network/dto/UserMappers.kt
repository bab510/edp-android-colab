package com.example.semifinals.data.network.dto

import com.example.semifinals.domain.model.User

// Turns the server's messy shape into the app's clean shape.
fun UserDto.toDomain(): User = User(
    id = id ?: "",
    fullName = fullname?.trim() ?: "(no name)",
    email = email?.trim() ?: "",
    birthdate = birthdate ?: "(not set)"
)
