package com.devcraft.tores.data.repositories.impl.net

object ApiConstants {
    const val API_BASE_URL = "https://backend.toresbusiness.com/"

    const val API_ENDPOINT_LOGIN = "api/auth/loginApp"
    const val API_ENDPOINT_USER = "api/auth/user"
    const val API_ENDPOINT_DASHBOARD = "api/dashboard"

    const val API_ENDPOINT_FINANCE = "api/finance"
    const val API_ENDPOINT_TOPUPS_AND_WITHDRAWALS = "api/finance/topupsAndWithdrawals"
    const val API_ENDPOINT_MINING_HISTORY = "api/miningHistory"
    const val API_ENDPOINT_CANCEL_WITHDRAW_FROM_DEPOSIT = "api/finance/cancelWithdrawFromDeposit"
    const val API_ENDPOINT_TRANSFERS_HISTORY = "api/finance/transfersHistory"
    const val API_ENDPOINT_REFERRAL_PROFITS_HISTORY = "api/finance/referralProfitsHistory"
    const val API_ENDPOINT_CANCEL_TOPUP = "api/finance/cancelTopUp"
    const val API_ENDPOINT_SUBMIT_TAC = "api/finance/submitTAC"
}
