package com.retrox

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.retrox.server.ConnectionHandler
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.InetSocketAddress
import java.util.*

class RealServer(address: InetSocketAddress = InetSocketAddress(4444)) : WebSocketServer(address) {
    var conectionHandler: ConnectionHandler? = null

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        conn.send("Welcome to server ${handshake.resourceDescriptor}")
        println("New Connection ${conn.remoteSocketAddress} ${handshake.resourceDescriptor}")
        conectionHandler = ConnectionHandler(conn)
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        println("DisConnected : $conn")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        println("Receive Message $conn $message")
        conn.send("FUCK YOU")
        conectionHandler?.offerMessage(message)
    }

    override fun onStart() {
        println("WS Start")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        println("WS ERROR")
        ex?.printStackTrace()
    }

}

fun main() {
    val server = RealServer()
    server.start()
    val jsonParser = JsonParser()
    val connectionHandler by lazy {
        server.conectionHandler
    }

    val sysin = BufferedReader(InputStreamReader(System.`in`))
    while (true) {
        val line = sysin.readLine()

        try {
            val jsonObject = jsonParser.parse(line).asJsonObject
            val code: JsonElement? = jsonObject.get("code")
            code?.let {
                println("Send Message Block, waiting for response")
                val response = connectionHandler?.sendMessageBlock(line, it.asInt)
                println("Send Message Block, received response: $response")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        connectionHandler?.sendMessage(line)

//        server.broadcast(line)
        if (line == "exit") {
            server.stop(1000)
            break
        }
    }

}