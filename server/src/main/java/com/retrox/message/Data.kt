package com.retrox.message

data class Message<T>(val code: Long, val status: String, val data: T)
