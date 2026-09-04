package com.finance.lumora.domain.model.ai


//This tells the UI who sent the message.
enum class ChatMessageRole {
    USER,
    AURIX
}

enum class ChatMessageStatus {
    SENT,
    LOADING,
    ERROR
}


/**
* Each message gets:
id → uniquely identifies the message
role → USER or AURIX
content → actual message text
**/
data class ChatMessage(
    val id: Long,
    val role: ChatMessageRole,
    val content: String,
    val status: ChatMessageStatus = ChatMessageStatus.SENT
)