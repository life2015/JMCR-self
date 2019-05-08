package com.retrox

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.retrox.server.ConnectionHandler
import com.retrox.server.ConnectionManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.java_websocket.WebSocket
import org.java_websocket.drafts.Draft
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ServerHandshakeBuilder
import org.java_websocket.server.WebSocketServer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.InetSocketAddress
import java.util.*



class RealServer(address: InetSocketAddress = InetSocketAddress(4444)) : WebSocketServer(address) {
    var conectionHandler: ConnectionHandler? = null
    val channel = Channel<ConnectionHandler>(1) // 暂时设计成单通道 不考虑多个连接


    override fun onWebsocketHandshakeReceivedAsServer(conn: WebSocket?, draft: Draft?, request: ClientHandshake?): ServerHandshakeBuilder {
        val builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request)
        builder.put("Access-Control-Allow-Origin", "*")
        return builder
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        conn.send("Welcome to server ${handshake.resourceDescriptor}")
        println("New Connection ${conn.remoteSocketAddress} ${handshake.resourceDescriptor}")
        val localConectionHandler = ConnectionHandler(conn)
        ConnectionManager.connectionHandler = localConectionHandler // 暂时给他一个全局性的连接
        conectionHandler = localConectionHandler
        channel.offer(localConectionHandler)
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        println("DisConnected: $conn")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        println("Receive Message: $conn $message")
        conectionHandler?.offerMessage(message)
    }

    override fun onStart() {
        println("WS Start")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        println("WS ERROR")
        ex?.printStackTrace()
    }

    fun awaitConnection() = runBlocking {
        println("await connection")
        channel.receive()
        println("await connection finished")
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
            val code: JsonElement? = jsonObject.get("response_code")
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