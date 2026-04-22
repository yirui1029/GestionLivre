package com.yirui.domain.model

data class Book(
    val title: String,
    val author: String,
    // créer la variable reserved, il s'agit une variable Boolean
    val reserved: Boolean = false
)
