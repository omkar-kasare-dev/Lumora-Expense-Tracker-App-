package com.finance.lumora.data.mapper



import com.finance.lumora.data.local.entity.NewsEntity
import com.finance.lumora.data.remote.dto.ArticleDto
import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun ArticleDto.toEntity(scope: String): NewsEntity {
    val parsedTime = try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        sdf.parse(publishedAt)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
    return NewsEntity(
        id = uuid,
        title = title,
        snippet = description,
        source = source,
        url = url,
        imageUrl = imageUrl,
        publishedAt = parsedTime,
        scope = scope,
        fetchedAt = System.currentTimeMillis()
    )
}

fun NewsEntity.toDomain(): NewsArticle = NewsArticle(
    id = id,
    title = title,
    snippet = snippet,
    source = source,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    scope = NewsScope.valueOf(scope)
)