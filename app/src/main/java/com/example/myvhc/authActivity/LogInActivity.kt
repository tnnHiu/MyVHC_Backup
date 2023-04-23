package com.example.myvhc.authActivity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myvhc.MainActivity
import com.example.myvhc.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.example.myvhc.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //kết nối firebase authentication
        auth = Firebase.auth

        //tạo tham số lấy API gmail đăng nhập
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        //khi mở app sẽ tự động đăng nhập bằng gmail đã đn trc đó
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //tạo sự kiện nhấp vào nút đăng nhập bằng gmail
        binding.btnSIWGoogle.setOnClickListener {
            signIn()
        }

    }

    //xác minh gmail và cho phép đăng nhập
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //hàm onActivityResult sẽ truyền vào hàm firebaseAuthWithGoogle idToken của account
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }

    //truyền idToken vào chứng chỉ (credential) thông qua chứng chỉ cho phép đăng nhập và thực hiện hàm updateUI
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Đăng nhập thành công, cập nhật giao diện người dùng với thông tin người dùng đã đăng nhập
                val user = auth.currentUser
                updateUI(user)
            } else {
                Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                updateUI(null)
            }
        }
    }

    //đăng nhập thành công -> trang home
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //mở trang home bằng gmail đã đăng nhập khi mở app
//    private fun updateUILogged() {
//        val user = auth.currentUser
//        if (user != null) {
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            intent.putExtra("name", user.displayName)
//            intent.putExtra("userId", user.uid)
//            intent.putExtra("email", user.email)
//            startActivity(intent)
//            finish()
//        }
//    }

    companion object {
        const val RC_SIGN_IN = 1001
    }

}