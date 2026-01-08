package com.harukadev.types

enum class Permission(val bit: Long) {
    SEND_MESSAGES(1L shl 11),
    MANAGE_MESSAGES(1L shl 13)
}