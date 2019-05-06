package com.retrox

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import java.util.*
import java.io.InputStreamReader
import java.io.BufferedReader


class RealClient(serverUri: URI?) : WebSocketClient(serverUri) {
    override fun onOpen(handshakedata: ServerHandshake) {
        println("Server HandShake: $handshakedata")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
    }

    override fun onMessage(message: String?) {
        println("Server Message: $message")
    }

    override fun onError(ex: Exception?) {
    }

}

fun main() {
    val client = RealClient(URI("ws://localhost:4444"))
    client.connect()

    val sysin = BufferedReader(InputStreamReader(System.`in`))
    while (true) {
        val line = sysin.readLine()
        client.send(line)
        if (line == "exit") {
            client.close(1000)
            break
        }
    }

}
