package com.finance.lumora.domain.model


data class Category(
    val id: Long = 0L,
    val name: String,
    val icon: String,
    val color: Long,
    val isDefault: Boolean = false
)