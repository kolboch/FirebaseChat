package com.kb.firechat.model

import java.util.*

data class ChatMessage(
        var messageText: String = "",
        var messageAuthor: String = "",
        var messageTime: Long = Date().time
)