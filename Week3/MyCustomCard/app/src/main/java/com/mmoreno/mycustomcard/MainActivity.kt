package com.mmoreno.mycustomcard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mmoreno.mycustomcard.model.DataRepository
import com.mmoreno.mycustomcard.util.AnimationType
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Main Activity - Entry Point
 * @author Mario
 */
class MainActivity : AppCompatActivity() {

    companion object {
        val HAS_SHOWN_INSTRUCTIONS_KEY = "MainActivity.hasShownInstructions"
        val CARD_POSITION_KEY = "MainActivity.cardPosition"
    }

    private var hasShownInstructions = false
    private val customCards = DataRepository.listOfCustomCards;
    private var cardPosition = 0

    /**
     * Overriding default onCreate metho
     * @param savedInstanceState Bundle provided to store/retrieve data
     * First time the Activity is created it is null
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        extractSavedState(savedInstanceState)
        showInstructions()
        setListeners()
        showCustomCard()
    }

    /**
     * Function to show the object representing a custom card
     * based on the cardPosition instance variable
     * @return [Unit]
     */
    private fun showCustomCard() {
        val card = customCards.get(cardPosition)
        programmingLanguage.text = card.programmingLanguage
        logoProgrammingLanguage.setImageResource(getResourceId(card.resourceName))
        experience.text = card.experience
    }

    /**
     * Custom function to set all view listeners
     * @return [Unit]
     */
    private fun setListeners() {
        previousProgrammingLanguage.setOnClickListener {
            getPreviousProgrammingLanguage(it)
        }

        nextProgrammingLanguage.setOnClickListener {
            getNextProgrammingLanguage(it)
        }

        facebookIcon.setOnClickListener {
            it.startAnimation(getAnimation(AnimationType.SOCIAL_MEDIA))
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.facebook.com/mm0ren0")
                )
            )
        }

        instagramIcon.setOnClickListener {
            it.startAnimation(getAnimation(AnimationType.SOCIAL_MEDIA))
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/mahumo92/")
                )
            )
        }

        twitterIcon.setOnClickListener {
            it.startAnimation(getAnimation(AnimationType.SOCIAL_MEDIA))
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/mahumogt")
                )
            )

        }

    }

    /**
     * Function to show the previous Custom Card Programming Language
     * @param view the button pressed by the user, if it is the first element
     * the method is not going to animate the button
     * @return [Unit]
     */
    private fun getPreviousProgrammingLanguage(view: View) {
        if (cardPosition - 1 >= 0) {
            cardPosition--
            view.startAnimation(getAnimation(AnimationType.PREVIOUS))
            showCustomCard()
        } else {
            Toast.makeText(this, getString(R.string.firstElementNotification), Toast.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * Function to show the next Custom Card Programming Language
     * @param view the button pressed by the user, if it is the last element
     * the method is not going to animate the button
     * @return [Unit]
     */
    private fun getNextProgrammingLanguage(view: View) {
        if (cardPosition + 1 < customCards.size) {
            cardPosition++
            view.startAnimation(getAnimation(AnimationType.NEXT))
            showCustomCard()

        } else {
            Toast.makeText(this, R.string.lastElementNotification, Toast.LENGTH_SHORT).show()
        }
        nextProgrammingLanguage.visibility = View.GONE
    }

    /**
     * Function to load an Animation defined in XML and use it
     * to animate a View
     * @return [Animation] Animation object for animate a view
     */
    private fun getAnimation(animationType: AnimationType): Animation {
        return when (animationType) {
            AnimationType.NEXT, AnimationType.PREVIOUS -> AnimationUtils.loadAnimation(
                this,
                R.anim.scale
            )
            AnimationType.SOCIAL_MEDIA -> AnimationUtils.loadAnimation(this, R.anim.rotate)
        }
    }

    /**
     * Function to show an instructions dialog to let the user
     * know what can do with the app
     * @return [Unit]
     */
    private fun showInstructions() {

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
        cardPosition =
            savedInstanceState?.getInt(CARD_POSITION_KEY, 0) ?: 0
    }

    /**
     * Overriding default method for saving custom variables
     * [outState] bundle available for storing key-value pairs
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(HAS_SHOWN_INSTRUCTIONS_KEY, hasShownInstructions)
        outState.putInt(CARD_POSITION_KEY, cardPosition)
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



