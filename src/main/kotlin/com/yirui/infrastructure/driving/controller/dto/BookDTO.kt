package com.yirui.infrastructure.driving.controller.dto
import jakarta.validation.constraints.NotBlank


data class BookDTO(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val author: String,
    // ajouter la variable reserved sur DTO
    val reserved: Boolean = false
)
