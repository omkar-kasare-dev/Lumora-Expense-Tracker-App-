package com.finance.lumora.data.mapper


import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.domain.model.Category

/**
 * Converts CategoryEntity to Category domain model.
 */
fun CategoryEntity.toDomain(): Category {

    return Category(
        id = id,
        name = name,
        icon = icon,
        color = color,
        isDefault = isDefault
    )
}

/**
 * Converts Category domain model to CategoryEntity.
 */
fun Category.toEntity(): CategoryEntity {

    return CategoryEntity(
        id = id,
        name = name,
        icon = icon,
        color = color,
        isDefault = isDefault
    )
}

/**
 * Converts Entity List to Domain List.
 */
fun List<CategoryEntity>.toDomainList(): List<Category> {

    return map {
        it.toDomain()
    }
}

/**
 * Converts Domain List to Entity List.
 */
fun List<Category>.toEntityList(): List<CategoryEntity> {

    return map {
        it.toEntity()
    }
}