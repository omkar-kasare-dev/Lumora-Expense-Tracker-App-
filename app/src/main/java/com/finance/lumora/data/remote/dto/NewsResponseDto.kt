package com.finance.lumora.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsResponseDto(
    @SerializedName("data") val data: List<ArticleDto>
)