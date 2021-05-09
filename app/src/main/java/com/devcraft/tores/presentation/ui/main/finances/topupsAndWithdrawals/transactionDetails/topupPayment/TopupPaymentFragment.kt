package com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails.topupPayment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.devcraft.tores.R
import com.devcraft.tores.entities.Currency
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import com.devcraft.tores.presentation.base.BaseFragment
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails.TransactionDetailsViewModel
import com.devcraft.tores.utils.extensions.setGone
import com.devcraft.tores.utils.extensions.setSafeOnClickListener
import com.devcraft.tores.utils.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_topup_payment.*
import kotlinx.android.synthetic.main.include_progressbar_overlay.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.net.URLEncoder


class TopupPaymentFragment : BaseFragment(R.layout.fragment_topup_payment) {
    override val vm: TransactionDetailsViewModel by sharedViewModel()
    private val vmFinances: FinancesViewModel by sharedViewModel()

    var loadingFinished = true
    var redirect = false

    override fun onDestroy() {
        super.onDestroy()

        getBaseActivity()?.showMainTopBar()
        getBaseActivity()?.showBottomBar()
    }

    override fun initViews() {
        super.initViews()

        getBaseActivity()?.hideMainTopBar()
        getBaseActivity()?.hideBottomBar()
    }

    override fun initListeners() {
        super.initListeners()

        flClose.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initObservers() {
        super.initObservers()

        vm.someProcessAlive.observe(viewLifecycleOwner, {
            progress_overlay.setVisible(it)
        })
        vm.selectedTransaction.observe(viewLifecycleOwner, { t ->
            if (t != null) {
                wv.setVisible()

                if (t.currency == Currency.PERFECTMONEY || t.currency == Currency.PAYEER) {
                    wv.settings.javaScriptEnabled = true

                    wv.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView, request: WebResourceRequest
                        ): Boolean {
                            if (!loadingFinished) {
                                redirect = true
                            }
                            loadingFinished = false

                            val url = request.url.toString()
                            if (url == "https://backend.toresbusiness.com/payment/pm/status") {
                                Log.d("wv_load", "status")
                            }

                            if (url == "https://backend.toresbusiness.com/payment/pm/payment") {
                                Log.d("wv_load", "payment")
                            }

                            if (url == "https://app.toresbusiness.com/finance/topup?payment=status") {
                                Log.d("wv_load", "status")
                            }

                            if (url == "https://app.toresbusiness.com/finance/topup?payment=success") {
                                Log.d("wv_load", "success")
                                showToast("Payment success")
                                vmFinances.needRefreshTopupsAndWithdrawals.postValue(true)
                                vm.refreshData()
                                requireActivity().onBackPressed()
                            }

                            if (url == "https://app.toresbusiness.com/finance/topup?payment=failed") {
                                Log.d("wv_load", "failed")
                                showToast("Payment failed")
                                requireActivity().onBackPressed()
                            } else {
                                wv?.loadUrl(url)
                            }

                            wv?.loadUrl(url)
                            return true
                        }

                        override fun onPageStarted(
                            view: WebView, url: String, favicon: Bitmap?
                        ) {
                            super.onPageStarted(view, url, favicon)
                            loadingFinished = false
                            progress_overlay?.setVisible()
                        }

                        override fun onPageFinished(view: WebView, url: String) {
                            if (!redirect) {
                                loadingFinished = true
                                progress_overlay?.setGone()
                            } else {
                                redirect = false
                            }
                        }
                    }


                    if (t.currency == Currency.PERFECTMONEY) {
                        val url = "https://perfectmoney.is/api/step1.asp"
                        wv.postUrl(url, generatePerfectMoneyPostData(t))
                    } else if (t.currency == Currency.PAYEER) {
                        val url = "https://payeer.com/merchant/"
                        wv.postUrl(url, generatePayeerPostData(t))
                    }
                }
            } else {
                wv.setGone()
            }
        })
    }

    private fun generatePerfectMoneyPostData(t: TopupsAndWithdrawalsData.Transaction): ByteArray {
        val postData =
            "PAYEE_ACCOUNT=${URLEncoder.encode("U25150150", "UTF-8")}" +
                    "&PAYEE_NAME=${URLEncoder.encode("Tores", "UTF-8")}" +
                    "&PAYMENT_AMOUNT=${
                        URLEncoder.encode(
                            t.amount.toString(),
                            "UTF-8"
                        )
                    }" +
                    "&PAYMENT_UNITS=${URLEncoder.encode("USD", "UTF-8")}" +
                    "&PAYMENT_ID=${URLEncoder.encode(t.id.toString(), "UTF-8")}" +
                    "&STATUS_URL=${
                        URLEncoder.encode(
                            "https://backend.toresbusiness.com/payment/pm/status",
                            "UTF-8"
                        )
                    }" +
                    "&PAYMENT_URL=${
                        URLEncoder.encode(
                            "https://backend.toresbusiness.com/payment/pm/payment",
                            "UTF-8"
                        )
                    }" +
                    "&NOPAYMENT_URL=${
                        URLEncoder.encode(
                            "https://backend.toresbusiness.com/payment/pm/failed",
                            "UTF-8"
                        )
                    }" +
                    "&BAGGAGE_FIELDS=${URLEncoder.encode("", "UTF-8")}" +
                    "&ORDER_NUM=${URLEncoder.encode(t.id.toString(), "UTF-8")}" +
                    "&CUST_NUM=${
                        URLEncoder.encode(
                            t.userId?.toString() ?: "",
                            "UTF-8"
                        )
                    }"
        return postData.toByteArray()
    }

    private fun generatePayeerPostData(t: TopupsAndWithdrawalsData.Transaction): ByteArray {
        val postData =
            "m_shop=${URLEncoder.encode("1226750343", "UTF-8")}" +
                    "&m_orderid=${URLEncoder.encode(t.id.toString(), "UTF-8")}" +
                    "&m_amount=${
                        URLEncoder.encode(
                            t.amount.toString(),
                            "UTF-8"
                        )
                    }" +
                    "&m_curr=${URLEncoder.encode("USD", "UTF-8")}" +
                    "&m_desc=${URLEncoder.encode("Topup", "UTF-8")}" +
                    "&m_sign=${
                        URLEncoder.encode(
                            t.payeerSign.orEmpty(),
                            "UTF-8"
                        )
                    }"
        return postData.toByteArray()
    }
}