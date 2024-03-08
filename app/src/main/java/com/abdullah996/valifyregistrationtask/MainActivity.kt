package com.abdullah996.valifyregistrationtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdullah996.registration.launcher.RegisterServiceLauncher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RegisterServiceLauncher.launchRegisterService(this)
    }
}
