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
    const val API_ENDPOINT_GET_AFFILIATE = "api/affiliate"
    const val API_ENDPOINT_GET_RANK_INFO = "api/ranks"
    const val API_ENDPOINT_GET_USER_RANK_SYSTEM_INFO = "api/ranksMyInfo"
    const val API_ENDPOINT_GET_AFFILIATE_TREE_FIRST_LINE = "api/affiliate/getTree"
    const val API_ENDPOINT_GET_AFFILIATE_TREE_SPECIFIC_LINE =
        "api/affiliate/getUserTree/{userId}/{line}"
    const val API_ENDPOINT_CHANGE_PASSWORD = "api/settings/change/password"
    const val API_ENDPOINT_SET_FINANCE_PASSWORD = "api/settings/change/pp"
    const val API_ENDPOINT_REMOVE_FINANCE_PASSWORD = "api/settings/reset/pp"

    const val API_ENDPOINT_GET_MINING_INFO = "api/mining"
    const val API_ENDPOINT_ADD_TO_MINING = "api/finance/reinvest"
    const val API_ENDPOINT_WITHDRAW_FROM_MINING = "api/finance/withdrawFromDeposit"

    const val API_ENDPOINT_GET_CURRENCY_RATES = "api/getRates"
    const val API_ENDPOINT_TOPUP = "api/finance/topup"
    const val API_ENDPOINT_WITHDRAW = "api/finance/withdrawal"
    const val API_ENDPOINT_TRANSFER_TO_USER = "api/finance/transferToUser"
    const val API_ENDPOINT_TRANSFER_TO_EXCHANGE = "api/finance/transferToExchange"

    const val API_ENDPOINT_GET_PARTNERS_AND_USER_REQUESTS = "api/partners/index"
    const val API_ENDPOINT_BECOME_PARTNER = "api/partners/submit"

    const val API_ENDPOINT_GET_SHARES_TOTAL_INFO = "api/shares/index"
    const val API_ENDPOINT_GET_SHARES_HISTORY = "api/shares/history"
    const val API_ENDPOINT_BUY_SHARES = "api/shares/buy"
    const val API_ENDPOINT_TRANSFER_SHARES = "api/shares/transfer"
}
