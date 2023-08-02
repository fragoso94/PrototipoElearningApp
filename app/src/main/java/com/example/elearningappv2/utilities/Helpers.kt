package com.example.elearningappv2.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class Helpers {
    companion object{
        const val COURSE_ID = "courseID"
        const val COURSE_ITEM = "courseITEM"
        const val IS_VIEW_BUY = "courseTYPE"
        const val URL_BASE_API = "https://apicursosbedu.servicesnet.site/api/" //"http://192.168.1.164:9095/"
        const val TAG = "dfragoso94"
        val PUBLIC_KEY = "key_PNPkyo2hK13YLb4FBwTpEMI"
        private val PRIVATE_KEY = "key_kR3tXnFCM0od3jSwPFNB8Js"
        val API_VERSION = "0.3.0"

        fun createUnsafeOkHttpClient(): OkHttpClient {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory

                OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    .addInterceptor(loggingInterceptor)
                    .hostnameVerifier { _, _ -> true }
                    .build()
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("Failed to create unsafe OkHttpClient", e)
            } catch (e: KeyManagementException) {
                throw RuntimeException("Failed to create unsafe OkHttpClient", e)
            }
        }

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnectedOrConnecting
            }
        }
    }
}