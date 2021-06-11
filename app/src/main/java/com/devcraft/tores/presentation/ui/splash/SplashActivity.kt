package com.devcraft.tores.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.devcraft.tores.BuildConfig
import com.devcraft.tores.R
import com.devcraft.tores.presentation.base.BaseActivity
import com.devcraft.tores.presentation.ui.auth.AuthActivity
import com.devcraft.tores.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    override val vm: SplashViewModel by viewModel()

    @SuppressLint("SetTextI18n")
    override fun initViews() {
        super.initViews()

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
                        val i = Intent(this, AuthActivity::class.java)
                        startActivity(i)
                    }
                    finish()
                }, 2000
            )
    }
}