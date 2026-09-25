package com.finance.lumora.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * One item of Finnhub's /news response.
 * `datetime` is UNIX time in SECONDS (the mapper converts to milliseconds).
 */
data class FinnhubArticleDto(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("datetime") val datetime: Long? = null,
    @SerializedName("headline") val headline: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("related") val related: String? = null,
    @SerializedName("source") val source: String? = null,
    @SerializedName("summary") val summary: String? = null,
    @SerializedName("url") val url: String? = null
)