package com.kb.firechat.data_adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.kb.firechat.model.ChatMessage
import kotlinx.android.synthetic.main.message_item.view.*

class ChatMessageViewHolder(
        view: View,
        private val itemClick: (ChatMessage) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bindMessageToView(message: ChatMessage) {
        with(message) {
            itemView.messageAuthor.text = messageAuthor
            itemView.messageText.text = messageText
            itemView.setOnClickListener { itemClick(this) }
        }
    }

}