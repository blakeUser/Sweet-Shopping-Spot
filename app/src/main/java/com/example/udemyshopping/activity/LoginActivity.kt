package com.example.udemyshopping.activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.udemyshopping.R
import com.example.udemyshopping.databinding.ActivityLoginBinding
import com.example.udemyshopping.firestore.FirestoreClass
import com.example.udemyshopping.models.User
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login) //TODO: Seems like different ways to access the view, in this case we chose binding
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding.tvForgotPassword.setOnClickListener(this)

        binding.btnLogin.setOnClickListener(this)

        binding.tvRegister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            println("Clicked something")
            when (view.id) {
                R.id.tv_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_login -> {
                    logInRegisteredUser()
                }
                R.id.tv_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun userLoggedInSuccessAndHeadToAmazon(user: User) {

        Log.i("First Name: ", user.firstName)
        Log.i("Last Name: ", user.lastName)
        Log.i("Email: ", user.email)

        // Hide the progress dialog.
        hideProgressDialog()
        // Print the user details in the log as of now.
        // Redirect the user to Main Screen after log in.
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))
            // Get the text from editText and trim the space
            val email : String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password : String = binding.etPassword.text.toString().trim { it <= ' ' }
            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    // Hide the progress dialog
                    if (task.isSuccessful) {
                        hideProgressDialog()
                        FirestoreClass().logInDetailsAndVerification(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
}