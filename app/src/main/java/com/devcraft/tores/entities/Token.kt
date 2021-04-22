package com.devcraft.tores.entities

data class Token(
    var token: String
){
    val bearerToken = "Bearer $token"
}