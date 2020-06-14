package com.mmoreno.mycustomcard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Main Activity - Entry Point
 * @author Mario
 */
class MainActivity : AppCompatActivity() {

    companion object {
        val HAS_SHOWN_INSTRUCTIONS_KEY = "MainActivity.hasShownInstructions"
    }

    private var hasShownInstructions = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showInstructions(savedInstanceState)

        setListeners()
    }

    /**
     * Custom function to set all view listeners
     * @return [Unit]
     */
    private fun setListeners() {
        previousProgrammingLanguage.setOnClickListener {
            it.startAnimation(getAnimation("Previous"))
        }
        nextProgrammingLanguage.setOnClickListener {
            it.startAnimation(getAnimation("Next"))
        }

        facebookIcon.setOnClickListener {
            it.startAnimation(getAnimation("SocialMedia"))
        }
        instagramIcon.setOnClickListener {
            it.startAnimation(getAnimation("SocialMedia"))
        }
        twitterIcon.setOnClickListener {
            it.startAnimation(getAnimation("SocialMedia"))
        }
    }

    /**
     * Function to load an Animation defined in XML and use it
     * to animate a View
     * @return [Animation] Animation object for animate a view
     */
    private fun getAnimation(useOfAnimation: String): Animation {
        return when (useOfAnimation) {
            "Previous", "Next" -> AnimationUtils.loadAnimation(this, R.anim.scale)
            else -> AnimationUtils.loadAnimation(this, R.anim.rotate)
        }
    }

    /**
     * Function to show an instructions dialog to let the user
     * know what can do with the app
     * @param [Bundle] Bundle provided to store/retrieve data
     * @return [Unit]
     */
    private fun showInstructions(savedInstanceState: Bundle?) {
        extractSavedState(savedInstanceState)

        if (!hasShownInstructions) {
            AlertDialog.Builder(this)
                .setView(R.layout.dialog_instructions)
                .setPositiveButton(getString(R.string.positiveButtonInstructiosDialog)) { dialog, _ ->
                    hasShownInstructions = true
                    dialog.dismiss()
                }
                .create()
                .show()

        }
    }

    /**
     * Custom function for extracting saved state of the activity
     * in the Bundle provided, first time the Activity is created
     * this instance is null
     * @param [savedInstanceState]: Bundle provided to store data
     * @return [Unit]
     */
    private fun extractSavedState(savedInstanceState: Bundle?) {
        hasShownInstructions =
            savedInstanceState?.getBoolean(HAS_SHOWN_INSTRUCTIONS_KEY, false) ?: false
    }

    /**
     * Overriding default method for saving custom variables
     * [outState] bundle available for storing key-value pairs
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(HAS_SHOWN_INSTRUCTIONS_KEY, hasShownInstructions)
        super.onSaveInstanceState(outState)
    }

    /**
     * Overriding default method for adding a Menu
     * @param [menu] object Menu provided to inflate our menu
     * @return [Boolean] returning true let the Activity know
     * that the menu is setup
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }

    /**
     * Overriding default method for managing options selected
     * in the menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionAbout -> {
                showAboutDialog()
                true
            }
            else -> false
        }
    }

    /**
     * Function to show a Dialog with information about the application
     * and its developer
     * @return [Unit]
     */
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle(
                getString(
                    R.string.aboutDialogTitle,
                    getString(R.string.app_name),
                    BuildConfig.VERSION_NAME
                )
            )
            .setMessage(getString(R.string.aboutMessage))
            .create()
            .show()
    }

    /**
     * Function to get the identifier of the resource
     * based on the name
     * @param [resourceName] Name of the image
     * @return [Int] Integer identifier of the resource
     */
    private fun getResourceId(resourceName: String): Int {
        return getResources().getIdentifier(resourceName, "drawable", getPackageName())
    }
}



