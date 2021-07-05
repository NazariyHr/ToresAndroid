package com.devcraft.tores.utils.extensions

fun String.nullIfEmpty(): String? {
    return if (this.isEmpty()) null else this
}