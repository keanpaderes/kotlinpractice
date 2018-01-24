package com.kean.android.kotlinpractice

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import kotlinx.android.synthetic.main.activity_google_sign_in.sign_in_button

class GoogleSignInActivity : AppCompatActivity() {
    companion object {
        const val SIGN_IN_TAG = "sign_in_tag"
        const val SIGN_IN_REQUEST_CODE = 1
    }


    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        val myToast = Toast.makeText(this, getString(R.string.server_client_id), Toast.LENGTH_SHORT)
        myToast.show()

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()

        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this, { task ->
            if (task.isSuccessful) {
                val account = task.result
                val idToken = account.idToken

                // Verify, then go to main activity intent
                goToMain(idToken)
            } else {
                // go to login activity
                sign_in_button.setOnClickListener {
                    val signInIntent = mGoogleSignInClient.signInIntent
                    startActivityForResult(signInIntent, GoogleSignInActivity.SIGN_IN_REQUEST_CODE)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GoogleSignInActivity.SIGN_IN_REQUEST_CODE) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.result
                val idToken = account.idToken


                val myToast = Toast.makeText(this, idToken, Toast.LENGTH_SHORT)
                myToast.show()
                // Verify, then go to main activity intent
                goToMain(idToken)
            } catch (e: ApiException) {
                Log.w("asd", "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }

    fun goToMain(token: String?) {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.putExtra(MainActivity.GOOGLE_ACCOUNT_TOKEN, token)
        startActivity(mainIntent)
    }
}
