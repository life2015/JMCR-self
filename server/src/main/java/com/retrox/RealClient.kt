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
        println("Server HandShake $handshakedata")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
    }

    override fun onMessage(message: String?) {
        println("Server Message $message")
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

object Test {
    fun test (x : Int) {
        println(x)
        val test = "a2222"
        println(test)
    }
}

class TEST222(val x: Int = 1, val y: Int = 2 , val z: Int = 3) {
    init {
        println("222")
    }
}

fun fuck() {
    val obj = TEST222(1,z =2,y = 3)
}