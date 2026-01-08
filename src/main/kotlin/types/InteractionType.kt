package com.harukadev.types

enum class InteractionType(val code: Int) {
    PING(1),
    APPLICATION_COMMAND(2),
    MESSAGE_COMPONENT(3),
    MODAL_SUBMIT(5)
}
