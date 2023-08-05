package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.amrdeveloper.lottiedialog.LottieDialog
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.ActivityPayCourseBinding
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.ui.viewmodel.PayCourseViewModel
import com.example.elearningappv2.utilities.Helpers
import dagger.hilt.android.AndroidEntryPoint
import io.conekta.conektasdk.Card
import io.conekta.conektasdk.Conekta
import io.conekta.conektasdk.Token
import org.json.JSONObject

@AndroidEntryPoint
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

        binding.btnTokenize.setOnClickListener {
            if (course != null) {
                onPressTokenizeButton(course.id)
            }
        }
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
        payCourseViewModel.navigateToHome.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToHome()
            }
        })
    }

    private fun onPressTokenizeButton(idCourse: Int) {
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
                token.onCreateTokenListener { data -> showTokenResult(data, idCourse) }
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

    private fun showTokenResult(data: JSONObject, idCourse: Int) {
        Log.e("dfragoso94", "showTokenResult: $data")
        /*
         {"object":"error","type":"parameter_validation_error","message":"The card number is invalid.",
         "message_to_purchaser":"El número de la tarjeta es inválido.","param":"card[card[number]]",
         "code":"invalid_number","validation_error":null}
         */
        try {
            if(data.has("id")){
                payCourseViewModel.shopCourse(idCourse)
                payCourseViewModel.responseModel.observe(this@PayCourseActivity, Observer
                {
                    if (it) {
                        showDialogLottie("Gracias por realizar la compra.", !it)
                    }
                })
            }
            else{
                showDialogLottie(data.getString("message_to_purchaser"), true)
            }
        }
        catch (error: Exception){
            Log.d("dfragoso94", error.message.toString())
        }
        enableInputs(true)
        enableProgressBar(false)
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

    private fun showDialogLottie(msg: String, isError:Boolean){
        lateinit var dialog : LottieDialog
        val okButton = Button(this)
        okButton.setText("Entendido")
        okButton.setOnClickListener{
            if(isError){
                dialog.cancel()
            }
            else{
                payCourseViewModel.onHomeSelected()
            }
        }

        try{
            dialog = LottieDialog(this)
                .setAnimation(if (isError) R.raw.error else R.raw.succeful)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setTitle("Elearning")
                .setTitleColor(Color.BLACK)
                .setMessage(msg)
                .setMessageColor(Color.BLACK)
                .setDialogBackground(Color.WHITE)
                .setCancelable(false)
                .addActionButton(okButton)
                .setOnShowListener(DialogInterface.OnShowListener { dialogInterface: DialogInterface? -> })
                .setOnDismissListener(DialogInterface.OnDismissListener { dialogInterface: DialogInterface? -> })
                .setOnCancelListener(DialogInterface.OnCancelListener { dialogInterface: DialogInterface? -> })
            dialog.show()
        }
        catch (e: Exception){
            Log.d("dfragoso94", e.message.toString())
        }
    }

    private fun goToHome() {
        startActivity(HomeActivity.create(this))
    }
}