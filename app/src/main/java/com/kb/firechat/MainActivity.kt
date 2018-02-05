package com.kb.firechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

const val SIGN_IN_REQUEST_CODE = 7009

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupChatButtonListener()
        setupLoginButtonListener()
        setUpLogoutButtonListener()
    }

    override fun onResume() {
        super.onResume()
        checkLoggedStatus()
    }

    private fun setupChatButtonListener() {
        chatButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, R.string.successful_log_in, Toast.LENGTH_SHORT).show()
                onUserLoggedInActions(FirebaseAuth.getInstance().currentUser?.displayName)
            } else {
                Toast.makeText(this, R.string.failure_log_in, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLoggedStatus() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            onUserLoggedOffActions()
        } else {
            val user = FirebaseAuth.getInstance().currentUser!!
            Toast.makeText(this, getString(R.string.user_welcome, user.displayName), Toast.LENGTH_SHORT).show()
            onUserLoggedInActions(user.displayName)
        }
    }

    private fun setupLoginButtonListener() {
        loginButton.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE)
            } else {
                Toast.makeText(this, getString(R.string.already_logged_in), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpLogoutButtonListener() {
        logoutButton.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnCompleteListener({
                Toast.makeText(this, R.string.successful_log_out, Toast.LENGTH_SHORT).show()
                onUserLoggedOffActions()
            })
        }
    }

    private fun onUserLoggedOffActions() {
        chatButton.isEnabled = false
        userLoggedTextView.text = getString(R.string.logged_off)
        loginButton.visibility = View.VISIBLE
        logoutButton.visibility = View.GONE
    }

    private fun onUserLoggedInActions(userName: String?) {
        chatButton.isEnabled = true
        userLoggedTextView.text = getString(R.string.logged_in_as, userName ?: "NoName")
        loginButton.visibility = View.GONE
        logoutButton.visibility = View.VISIBLE
    }
}
