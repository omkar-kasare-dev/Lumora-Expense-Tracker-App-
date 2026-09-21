package com.finance.lumora.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("source") val source: String,
    @SerializedName("url") val url: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("published_at") val publishedAt: String
)