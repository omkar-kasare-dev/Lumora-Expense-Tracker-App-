package com.finance.lumora.data.mapper

import com.finance.lumora.data.local.entity.SubCategoryEntity
import com.finance.lumora.domain.model.SubCategory


    fun SubCategoryEntity.toDomain() =
        SubCategory(
            id = id,
            categoryId = categoryId,
            name = name,
            isDefault = isDefault
        )

    fun SubCategory.toEntity() =
        SubCategoryEntity(
            id = id,
            categoryId = categoryId,
            name = name,
            isDefault = isDefault
        )

    fun List<SubCategoryEntity>.toDomainList() =
        map { it.toDomain() }
