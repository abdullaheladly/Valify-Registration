package com.abdullah996.registration.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdullah996.registration.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }
}
