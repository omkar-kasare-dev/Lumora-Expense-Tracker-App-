package com.finance.lumora.data.local.converter



import androidx.room.TypeConverter
import com.finance.lumora.domain.model.NotificationType

class NotificationTypeConverter {

    @TypeConverter
    fun fromNotificationType(type: NotificationType): String {
        return type.name
    }

    @TypeConverter
    fun toNotificationType(value: String): NotificationType {
        return NotificationType.valueOf(value)
    }
}