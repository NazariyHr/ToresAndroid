package com.devcraft.tores.presentation.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.devcraft.tores.R
import com.devcraft.tores.presentation.ui.auth.AuthActivity
import com.devcraft.tores.presentation.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val vm: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper())
            .postDelayed(
                {
                    val token = vm.getToken()
                    val hasToken = token.isNotEmpty()
                    if (hasToken) {
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        val i = Intent(this, MainActivity::class.java)
                        //val i = Intent(this, AuthActivity::class.java)
                        startActivity(i)
                    }
                    finish()
                }, 2000
            )
    }
}