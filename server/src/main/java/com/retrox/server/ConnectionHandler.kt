package com.retrox.server

import com.google.gson.JsonParser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.java_websocket.WebSocket
import java.util.*

class ConnectionHandler(val connection: WebSocket) {
    private val historyMessage = LinkedList<String>()
    private val observers = mutableListOf<Observer>()
    private val pausedChannels = mutableMapOf<Int, Channel<String>>()
    private val jsonParser = JsonParser()
    private var message = ""
        set(value) {
            field = value
//            historyMessage.addLast(value)
            notifyAllObservers(value)
        }

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    /**
     * Called from SocketServer -> onMessage
     */
    fun offerMessage(message: String) {
        this.message = message
    }

    fun getCurrentMessage() = message

    /**
     * 向对应的客户端连接发送信息
     */
    fun sendMessage(message: String) {
        connection.send(message)
    }

    /**
     * 同步消息 RPC用 比如说那个Choose要交给远程选择，并且等待返回值
     */
    suspend fun sendMessageSync(message: String, code: Int): String {
        val channel = Channel<String>(1)
        pausedChannels[code] = channel
        sendMessage(message)
//        sendMessage()
        return channel.receive()
    }

    fun sendMessageBlock(message: String, code: Int): String = runBlocking {
        sendMessageSync(message, code)
    }

    private fun notifyAllObservers(message: String) {
        handleMessage(message)
        observers.forEach {
            it.onChange(message)
        }
    }

    private fun handleMessage(message: String) {
        val jsonObject = try {
            jsonParser.parse(message).asJsonObject
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        val code = jsonObject.get("response_code").asInt
        val channel = pausedChannels[code]
        if (channel == null) {
            sendMessage("Wrong Sync Code: $code")
        }
        channel?.offer(message) // todo 检查可靠性 也许需要Block掉
        pausedChannels.remove(code) // 去除阻塞Channel的订阅
    }

    interface Observer {
        fun onChange(message: String)
    }
}