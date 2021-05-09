package com.devcraft.tores.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.devcraft.tores.BuildConfig
import com.devcraft.tores.R
import com.devcraft.tores.presentation.ui.auth.AuthActivity
import com.devcraft.tores.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val vm: SplashViewModel by viewModel()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        tvVersion.text = "version ${BuildConfig.VERSION_NAME}"

        Handler(Looper.getMainLooper())
            .postDelayed(
                {
                    val token = vm.getToken()
                    val hasToken = token.isNotEmpty()
                    if (hasToken) {
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        //val i = Intent(this, MainActivity::class.java)
                        val i = Intent(this, AuthActivity::class.java)
                        startActivity(i)
                    }
                    finish()
                }, 2000
            )
    }
}