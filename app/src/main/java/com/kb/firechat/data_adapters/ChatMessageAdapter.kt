package com.kb.firechat.data_adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kb.firechat.R
import com.kb.firechat.model.ChatMessage

class ChatMessageAdapter(
        private val messages: ArrayList<ChatMessage>,
        private val itemClick: (ChatMessage) -> Unit
) : RecyclerView.Adapter<ChatMessageViewHolder>() {

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ChatMessageViewHolder?, position: Int) {
        holder?.bindMessageToView(messages[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatMessageViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.message_item, parent, false)
        return ChatMessageViewHolder(view, itemClick)
    }

}