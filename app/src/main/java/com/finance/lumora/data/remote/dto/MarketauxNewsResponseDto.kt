package com.finance.lumora.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Every field is nullable with a default: Gson ignores Kotlin null-safety, so a
 * missing field must not become a null inside a non-null property.
 * These replace the old NewsResponseDto / ArticleDto (which you can delete).
 */
data class MarketauxNewsResponseDto(
    @SerializedName("meta") val meta: MarketauxMetaDto? = null,
    @SerializedName("data") val data: List<MarketauxArticleDto>? = null
)

data class MarketauxMetaDto(
    @SerializedName("found") val found: Int? = null,
    @SerializedName("returned") val returned: Int? = null,
    @SerializedName("limit") val limit: Int? = null,
    @SerializedName("page") val page: Int? = null
)

data class MarketauxArticleDto(
    @SerializedName("uuid") val uuid: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("snippet") val snippet: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("published_at") val publishedAt: String? = null,
    @SerializedName("source") val source: String? = null
)