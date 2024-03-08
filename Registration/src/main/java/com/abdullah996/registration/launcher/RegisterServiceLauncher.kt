package com.abdullah996.registration.launcher

import android.content.Context
import android.content.Intent
import com.abdullah996.registration.presentation.RegistrationActivity

object RegisterServiceLauncher {
    fun launchRegisterService(context: Context) {
        val intent = Intent(context, RegistrationActivity::class.java)
        context.startActivity(intent)
    }
}
