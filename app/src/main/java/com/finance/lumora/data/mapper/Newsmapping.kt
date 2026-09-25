package com.finance.lumora.data.mapper

import com.finance.lumora.data.local.entity.NewsEntity
import com.finance.lumora.data.remote.dto.FinnhubArticleDto
import com.finance.lumora.data.remote.dto.MarketauxArticleDto
import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/*
 * Function names are new (toNewsEntity / toNewsArticle) so this file can live next to
 * your old mapper without "conflicting overloads". Delete the old mapper when convenient.
 *
 * Row ids are stored as "<SCOPE>|<providerId>". That makes the primary key unique per
 * scope (the same story can no longer overwrite its twin in the other feed) and needs
 * NO schema change or migration. The "|" also lets the DAO recognise and drop old rows.
 */

private const val ID_SEPARATOR = "|"
private val WHITESPACE = Regex("\\s+")

private fun scopedId(scope: NewsScope, rawId: String) = "${scope.name}$ID_SEPARATOR$rawId"

private fun String?.cleanText(): String = this?.replace(WHITESPACE, " ")?.trim().orEmpty()

private fun String?.httpUrlOrNull(): String? =
    this?.trim()?.takeIf {
        it.startsWith("https://", ignoreCase = true) || it.startsWith("http://", ignoreCase = true)
    }

/** Upgrades http:// images to https:// (Android blocks cleartext traffic by default). */
private fun String?.imageUrlOrNull(): String? =
    httpUrlOrNull()?.let {
        if (it.startsWith("http://", ignoreCase = true)) "https://" + it.substring(7) else it
    }

/**
 * Marketaux sends e.g. "2024-11-08T01:24:00.000000Z" (always UTC). Only the first
 * 19 characters are needed, which works on every API level without desugaring.
 */
private fun parseIsoUtcMillis(value: String): Long? =
    runCatching {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
            isLenient = false
        }
        format.parse(value.trim().take(19))?.time
    }.getOrNull()

/** Returns null for unusable articles so one bad item never breaks a whole batch. */
fun MarketauxArticleDto.toNewsEntity(scope: NewsScope, fetchedAt: Long): NewsEntity? {
    val rawId = uuid?.trim()?.takeIf { it.isNotEmpty() } ?: return null
    val headline = title.cleanText().ifEmpty { return null }
    val link = url.httpUrlOrNull() ?: return null
    val published = publishedAt?.let { parseIsoUtcMillis(it) } ?: return null
    // Prefer the meta description; fall back to the (truncated) body snippet.
    val summary = description.cleanText().ifEmpty { snippet.cleanText() }

    return NewsEntity(
        id = scopedId(scope, rawId),
        title = headline,
        snippet = summary,
        source = source.cleanText().removePrefix("www."),
        url = link,
        imageUrl = imageUrl.imageUrlOrNull(),
        publishedAt = published,
        scope = scope.name,
        fetchedAt = fetchedAt
    )
}

fun FinnhubArticleDto.toNewsEntity(scope: NewsScope, fetchedAt: Long): NewsEntity? {
    val rawId = id?.toString() ?: return null
    val title = headline.cleanText().ifEmpty { return null }
    val link = url.httpUrlOrNull() ?: return null
    val seconds = datetime?.takeIf { it > 0L } ?: return null

    return NewsEntity(
        id = scopedId(scope, rawId),
        title = title,
        snippet = summary.cleanText(),
        source = source.cleanText(),
        url = link,
        imageUrl = image.imageUrlOrNull(),
        publishedAt = seconds * 1000L, // Finnhub uses seconds
        scope = scope.name,
        fetchedAt = fetchedAt
    )
}

fun NewsEntity.toNewsArticle(): NewsArticle = NewsArticle(
    id = id,
    title = title,
    snippet = snippet,
    source = source,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    scope = NewsScope.values().firstOrNull { it.name == scope } ?: NewsScope.GLOBAL
)