package com.retrox.router

import com.google.gson.JsonObject
import com.google.gson.JsonParser

interface MessageRouter {
    fun routeMessage(message: String)
}

object JsonMessageRouter : MessageRouter {
    val jsonParser = JsonParser()
    override fun routeMessage(message: String) {
        val jsonObject: JsonObject = try {
            jsonParser.parse(message).asJsonObject
        } catch (e: Exception) { // not a json string
            // todo add Logger
            return
        }


    }
}