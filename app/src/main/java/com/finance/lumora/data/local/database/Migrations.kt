package com.finance.lumora.data.local.database


import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `notifications` (
                `id` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `message` TEXT NOT NULL,
                `timestampMillis` INTEGER NOT NULL,
                `isRead` INTEGER NOT NULL,
                `type` TEXT NOT NULL,
                `actionUrl` TEXT,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
    }
}