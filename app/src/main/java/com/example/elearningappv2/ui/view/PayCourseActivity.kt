package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.ActivityPayCourseBinding
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.ui.viewmodel.PayCourseViewModel
import com.example.elearningappv2.utilities.Helpers
import io.conekta.conektasdk.Card
import io.conekta.conektasdk.Conekta
import io.conekta.conektasdk.Token
import org.json.JSONObject

class PayCourseActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context, course:Course): Intent
        {
            val intent = Intent(context, PayCourseActivity::class.java)
            intent.putExtra(Helpers.COURSE_ITEM, course)
            return intent
        }
    }

    lateinit var binding: ActivityPayCourseBinding
    private val payCourseViewModel: PayCourseViewModel by viewModels()

    private var hasValidCardData: Boolean? = false
    private var cardName: String? = null
    private var cardNumber: String? = null
    private var cardCvc: String? = null
    private var cardMonth: String? = null
    private var cardYear: String? = null
    private var tokenIdTag: String? = null
    private var errorTag: String? = null
    private var uuidDeviceTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayCourseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val course: Course? = intent.getParcelableExtra(Helpers.COURSE_ITEM)
        binding.txvTitle.text = ("Compra de " + course?.name)

        tokenIdTag = resources.getString(R.string.theTokenIdLabel) // The token id:
        errorTag = resources.getString(R.string.errorLabel) // Error:
        uuidDeviceTag = resources.getString(R.string.uuidDeviceLabel) // Uuid device:

        binding.btnTokenize.setOnClickListener { onPressTokenizeButton() }
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        enableInputs(true)
        enableProgressBar(false)
    }

    private fun initObservers(){
        payCourseViewModel.isLoading.observe(this, Observer{
            binding.progressBar.isVisible = it
        })
    }

    private fun onPressTokenizeButton() {
        enableInputs(false)
        enableProgressBar(true)
        if (hasInternetConnection()) {
            Conekta.setPublicKey(Helpers.PUBLIC_KEY)
            Conekta.setApiVersion(Helpers.API_VERSION)
            Conekta.collectDevice(this)
            getCardData()
            if (hasValidCardData!!) {
                val card = Card(cardName, cardNumber, cardCvc, cardMonth, cardYear)
                val token = Token(this)

                //Listen when token is returned
                token.onCreateTokenListener { data -> showTokenResult(data) }

                //Request for create token
                token.create(card)
            } else {
                Toast.makeText(
                    this@PayCourseActivity,
                    resources.getString(R.string.cardDataIncomplete),
                    Toast.LENGTH_LONG
                ).show()
                enableInputs(true)
                enableProgressBar(false)
            }
        } else {
            Toast.makeText(
                this@PayCourseActivity,
                resources.getString(R.string.needInternetConnection),
                Toast.LENGTH_LONG
            ).show()
            //binding.outputView.text = resources.getString(R.string.needInternetConnection)
            enableInputs(true)
            enableProgressBar(false)
        }
    }

    private fun enableProgressBar(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.shadowView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showTokenResult(data: JSONObject) {
        Log.e("dfragoso94", "showTokenResult: $data")
        /*try {
            val tokenId: String = if (data.has("id")) {
                data.getString("id")
            } else {
                data.getString("message")
            }
            val tokenMessage = "$tokenIdTag $tokenId"
            binding.outputView.text = tokenMessage
            Log.d(tokenIdTag, tokenId)
        } catch (error: Exception) {
            val errorMessage = "$errorTag $error"
            binding.outputView.text = errorMessage
        }
        enableInputs(true)
        enableProgressBar(false)

        val uuidMessage: String = uuidDeviceTag + " " + Conekta.deviceFingerPrint(this)
        binding.uuidDevice.text = uuidMessage*/
    }

    private fun hasInternetConnection(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun getCardData() {
        with(binding){
            hasValidCardData = true
            cardName = nameText.text.toString()
            cardNumber = numberText.text.toString()
            cardCvc = cvcText.text.toString()
            cardMonth = monthText.text.toString()
            cardYear = yearText.text.toString()
            if (cardName!!.isEmpty() || cardNumber!!.isEmpty() || cardCvc!!.isEmpty()
                || cardMonth!!.isEmpty() || cardYear!!.isEmpty()
            ) {
                hasValidCardData = false
            }
        }
    }

    private fun enableInputs(isEnable: Boolean) {
        with(binding){
            btnTokenize.isEnabled = isEnable
            numberText.isEnabled = isEnable
            nameText.isEnabled = isEnable
            monthText.isEnabled = isEnable
            yearText.isEnabled = isEnable
            cvcText.isEnabled = isEnable
        }
    }

}