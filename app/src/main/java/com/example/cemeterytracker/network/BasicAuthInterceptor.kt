package com.example.cemeterytracker.network

import android.content.SharedPreferences
import com.example.cemeterytracker.other.Constants
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(sharedPreferences: SharedPreferences)  : Interceptor {

    var userName = sharedPreferences.getString(Constants.KEY_LOGGED_IN_USERNAME, Constants.NO_USERNAME)
    var password = sharedPreferences.getString(Constants.KEY_PASSWORD, Constants.NO_PASSWORD)


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if(request.url.encodedPath in Constants.IGNORE_AUTH_URLS){
            return chain.proceed(request)
        }

        val authenticationRequest = request.newBuilder()
            .header("Authorization", Credentials.basic(userName ?: "", password ?: ""))
            .build()
        return chain.proceed(authenticationRequest)
    }
}