package com.finance.lumora.domain.search.model


import com.finance.lumora.data.local.enums.TransactionType

data class SearchFilters(

    val transactionType: TransactionType? = null,

    val categoryId: Long? = null,

    val startDate: Long? = null,

    val endDate: Long? = null,

    val minAmount: Double? = null,

    val maxAmount: Double? = null

)
