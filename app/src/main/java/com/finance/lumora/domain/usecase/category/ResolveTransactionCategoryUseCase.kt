package com.finance.lumora.domain.usecase.category


import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ResolveTransactionCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(
        categoryName: String?
    ): Category? {

        if (categoryName.isNullOrBlank()) {
            return null
        }

        val normalizedInput =
            normalizeCategoryName(categoryName)

        if (normalizedInput.isBlank()) {
            return null
        }

        // First try the existing exact lookup.
        categoryRepository
            .getCategoryByName(categoryName.trim())
            ?.let { return it }

        // If exact matching fails, compare against the
        // existing Lumora categories after normalization.
        return categoryRepository
            .getAllCategories()
            .first()
            .firstOrNull { category ->
                normalizeCategoryName(category.name) == normalizedInput
            }
    }

    private fun normalizeCategoryName(
        name: String
    ): String {

        return name
            .trim()
            .lowercase()
            .filter { character ->
                character.isLetterOrDigit() ||
                        character.isWhitespace()
            }
            .replace(
                Regex("\\s+"),
                " "
            )
            .trim()
    }
}