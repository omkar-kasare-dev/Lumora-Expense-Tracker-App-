package com.finance.lumora.presentation.search.state



sealed class FilterChipType {

    data object TransactionType : FilterChipType()

    data object Category : FilterChipType()

    data object MinAmount : FilterChipType()

    data object MaxAmount : FilterChipType()

    data object DateRange : FilterChipType()
}