package com.finance.lumora.data.cache



import android.content.Context

class CacheManager(
    private val context: Context
) {

    fun clearCache() {
        val cacheDirectory = context.cacheDir

        cacheDirectory
            .listFiles()
            ?.forEach { file ->
                file.deleteRecursively()
            }
    }
}