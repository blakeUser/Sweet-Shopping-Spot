package com.example.udemyshopping.activity
import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.udemyshopping.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: AlertDialog

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }
    fun showProgressDialog(text: String) {
        val builder = android.app.AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_progress,null)
        builder.setView(view)

        mProgressDialog = builder.create()
//        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.setMessage("给我等一会") //TODO : DELETE THIS LINE FOR CUSTOM MESSAGE

        mProgressDialog.setCancelable(false)

        mProgressDialog.setCanceledOnTouchOutside(false)
        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}