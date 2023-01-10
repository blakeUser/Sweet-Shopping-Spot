package com.example.udemyshopping.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.example.udemyshopping.R
import com.example.udemyshopping.databinding.ActivityLoginBinding
import com.example.udemyshopping.databinding.ActivityRegisterBinding
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
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        showErrorSnackBar("You are logged in successfully.", false)
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
}