package com.example.udemyshopping.activity
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.udemyshopping.R
import com.example.udemyshopping.models.User
import com.example.udemyshopping.databinding.ActivityUserProfileBinding

import com.example.udemyshopping.utils.Constants

@Suppress("DEPRECATION")
class UserProfileActivity : BaseActivity(), View.OnClickListener{

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        var userDetails: User = User()
        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            // Get the user details from intent as a ParcelableExtra.
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }
        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(userDetails.firstName)

        binding.etLastName.isEnabled = false
        binding.etLastName.setText(userDetails.lastName)

        binding.etEmail.isEnabled = false
        binding.etEmail.setText(userDetails.email)

        binding.ivUserPhoto.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {

                R.id.iv_user_photo -> {

                    // Here we will check if the permission is already allowed or we need to request for it.
                    // First of all we will check the READ_EXTERNAL_STORAGE permission and if it is not allowed we will request for the same.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {

                        showErrorSnackBar("You already have the storage permission.", false)
                    } else {

                        /*Requests permissions to be granted to this application. These permissions
                         must be requested in your manifest, they should not be granted to your app,
                         and they should have protection level*/

                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                showErrorSnackBar("The storage permission is granted.", false)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}