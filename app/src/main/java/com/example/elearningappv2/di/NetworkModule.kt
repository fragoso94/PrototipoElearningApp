package com.example.elearningappv2.di

import com.example.elearningappv2.data.network.CourseApiClient
import com.example.elearningappv2.utilities.Helpers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetroifit(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(Helpers.URL_BASE_API)
            .client(createUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit):CourseApiClient
    {
        return retrofit.create(CourseApiClient::class.java)
    }

    @Singleton
    @Provides
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
}

