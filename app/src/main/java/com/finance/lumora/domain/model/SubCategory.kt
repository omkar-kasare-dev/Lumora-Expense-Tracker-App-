package com.finance.lumora.domain.model

data class SubCategory(
    val id: Long = 0L,
    val categoryId: Long,
    val name: String,
    val isDefault: Boolean = false
)
