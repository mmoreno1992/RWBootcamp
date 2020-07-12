package com.mmoreno.favmovies.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.preference.PreferenceManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mmoreno.favmovies.MainActivity
import com.mmoreno.favmovies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    /**
     * Set of constants for managing and validating data
     */
    companion object {
        const val USER_LENGTH = 4
        const val PASSWORD_LENGTH = 5
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
        const val PROFILE_IMAGE_PATH = "PROFILE_IMAGE_PATH"
        const val USER = "USER"
    }

    private var fileUri: Uri? = null

    /**
     * Overriding onCreate default method
     * @param savedInstanceState bundle instance to save/extract the state
     * of the Activity
     * @return [Unit]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
        checkIfLoggedIn()

        /**
         *  The first time the Activity executes the bundle savedInstanceState
         *  is null, so we check if previously the user has checked "Remember Me"
         *  otherwise just check the savedInstanceState in case the user has already
         *  changed the profile image and the app receives a configuration change
         */
        if (savedInstanceState != null)
            extractSavedData(savedInstanceState)
        else
            checkIfRememberMe()
    }

    /**
     * Method for retrieving the imagePath for the profile
     * and user if the last time the user logged in checked the checkbox
     * to remember him/her
     * @return [Unit]
     */
    private fun checkIfRememberMe() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val uriProfileImage = preferences.getString(PROFILE_IMAGE_PATH, "").orEmpty()

        if (uriProfileImage.isNotEmpty()) {
            fileUri = Uri.parse(uriProfileImage)
            profileImageView.setImageURI(fileUri)
        }

        val user = preferences.getString(USER, "").orEmpty()
        if (user.isNotEmpty()) {
            userInputText.setText(user)
        }
    }

    /**
     * Method to retrieve the saved state for this Activity due to
     * configuration changes
     */
    private fun extractSavedData(savedInstanceState: Bundle?) {
        fileUri = savedInstanceState?.getParcelable(PROFILE_IMAGE_PATH)
        if (fileUri != null) {
            showSavedProfileImage(fileUri)
        }
    }

    /**
     * Method to display the profileImage in the login
     * @param file Uri instance for loading the image
     */
    private fun showSavedProfileImage(file: Uri?) {
        Picasso
            .get()
            .load(file)
            .into(profileImageView)
    }

    /**
     * Overriding method for saving state of the Activity
     * in configuration changes
     * @param outState instance of bundle to save the state
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(PROFILE_IMAGE_PATH, fileUri)
        super.onSaveInstanceState(outState)
    }

    /**
     * Method for checking if the user has logged in previously
     * making use of SharedPreferences
     * @return [Unit]
     */
    private fun checkIfLoggedIn() {
        val isLoggedIn = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(IS_LOGGED_IN, false)
        if (isLoggedIn) {
            openMainActivity()
        }
    }

    /**
     * Method for opening the MainActivity where the user can interact
     * with the list of movies saved in SQLite
     * @return [Unit]
     */
    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     * Method for setting all views' listeners
     * @return [Unit]
     */
    private fun setListeners() {
        uploadImageView.setOnClickListener {
            ImagePicker.with(this)
                //Final image size will be less than 1 MB(Optional)
                .compress(1024)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .maxResultSize(
                    1080,
                    1080
                )
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            fileUri = data?.data
                            profileImageView.setImageURI(fileUri)
                        }
                        //If an error occurs, we set a default image
                        ImagePicker.RESULT_ERROR -> {
                            profileImageView.setImageResource(R.drawable.ic_default_profile)
                            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                                .show()
                        }
                        //When the user presses back for example, the task is cancelled
                        else -> {
                            Toast.makeText(
                                this,
                                getString(R.string.canceledImage),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
        }

        TooltipCompat.setTooltipText(uploadImageView, getString(R.string.uploadProfileImageTooltip))
        loginButton.setOnClickListener {
            doLogin()
        }

        userInputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val length = s?.trim()?.length ?: 0
                if (length < USER_LENGTH) {
                    userInputLayout.error = getString(R.string.userError)
                } else {
                    userInputLayout.error = ""
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        passwordInputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val length = s?.trim()?.length ?: 0
                if (length < PASSWORD_LENGTH) {
                    passwordInputLayout.error = getString(R.string.passwordError)
                } else {
                    passwordInputLayout.error = ""
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    /**
     * Method for do the login and validate the credentials provided
     * in the [userInputText] and [passwordInputText] views
     * @return [Unit]
     */
    private fun doLogin() {
        val user = userInputText.text.toString()
        val password = passwordInputText.text.toString()

        if ((user.length < USER_LENGTH)
            || password.length < PASSWORD_LENGTH
        ) {
            Toast.makeText(
                this,
                getString(R.string.checkUserAndPasswordLengthLogin),
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        if (user == getString(R.string.user) && password == getString(R.string.password)) {
            saveLoggedInState()
            openMainActivity()
        }

    }

    /**
     * Method for saving a key-value Pair using SharedPreferences
     * if the user logs in successfully
     * @return [Unit]
     */
    private fun saveLoggedInState() {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        if (rememberMeCheckBox.isChecked) {
            if (fileUri != null)
                editor.putString(PROFILE_IMAGE_PATH, fileUri.toString())
            editor.putString(USER, userInputText.text.toString())
        } else {
            editor.putString(PROFILE_IMAGE_PATH, "")
            editor.putString(USER, "")
        }
        editor.apply()
    }
}
