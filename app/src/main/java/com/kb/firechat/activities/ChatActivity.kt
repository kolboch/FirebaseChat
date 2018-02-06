package com.kb.firechat.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kb.firechat.R
import com.kb.firechat.data_adapters.ChatMessageAdapter
import com.kb.firechat.model.ChatMessage
import kotlinx.android.synthetic.main.activity_chat.*

const val TEMP_CHAT_ID = "users_chat_1"

class ChatActivity : AppCompatActivity() {

    private val LOG_TAG = ChatActivity::class.java.simpleName
    private val databaseInstance = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        createFirebaseListener()
        setupSendMessageButton()
    }

    private fun setupSendMessageButton() {
        sendMessageButton.setOnClickListener {
            databaseInstance
                    .getReference(TEMP_CHAT_ID)
                    .push()
                    .setValue(
                            ChatMessage(textInput.text.toString(), FirebaseAuth.getInstance().currentUser?.displayName!!)
                    )
            textInput.setText("")
        }
    }

    private fun createFirebaseListener() {
        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.e(LOG_TAG, p0.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var results = ArrayList<ChatMessage>()
                snapshot.children
                        .map { it.getValue<ChatMessage>(ChatMessage::class.java) }
                        .mapNotNullTo(results) { it }

                results.sortBy { message -> message.messageTime }
                setupMessagesAdapter(results)
            }
        }
        databaseInstance.getReference(TEMP_CHAT_ID).addValueEventListener(postListener)
    }

    private fun setupMessagesAdapter(messages: ArrayList<ChatMessage>) {
        messagesRecycler.layoutManager = LinearLayoutManager(this)
        messagesRecycler.adapter = ChatMessageAdapter(
                messages, { Toast.makeText(this, "${it.messageText}", Toast.LENGTH_SHORT).show() }
        )
        messagesRecycler.scrollToPosition(messages.size - 1)
    }
}
