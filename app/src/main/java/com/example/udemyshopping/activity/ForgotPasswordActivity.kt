package com.example.udemyshopping.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.udemyshopping.R
import com.example.udemyshopping.databinding.ActivityBaseBinding.inflate
import com.example.udemyshopping.databinding.ActivityForgotPasswordBinding
import com.example.udemyshopping.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class ForgotPasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityForgotPasswordBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_forgot_password)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()

        binding.btnSubmit.setOnClickListener {

            // Get the email id from the input field.
            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }

            // Now, If the email entered in blank then show the error message or else continue with the implemented feature.
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {

                // Show the progress dialog.
                showProgressDialog(resources.getString(R.string.please_wait))

                // This piece of code is used to send the reset password link to the user's email id if the user is registered.
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        // Hide the progress dialog
                        hideProgressDialog()
                        if (task.isSuccessful) {
                            // Show the toast message and finish the forgot password activity to go back to the login screen.
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                resources.getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }
    }
}