package com.devcraft.tores.entities

data class ShareTransfer(
    var id: String,
    var type: ShareTransferType,
    var status: ShareTransferStatus,
    var createdAt: String,
    var login: String?,
    var amount: String,
    var sharesAmount: String
)
