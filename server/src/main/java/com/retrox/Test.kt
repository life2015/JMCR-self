package com.retrox

import com.google.gson.JsonParser
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

object Test {
    var x = 2

    fun changeNum() {
        x = 3
    }

    fun printNum() = x
}