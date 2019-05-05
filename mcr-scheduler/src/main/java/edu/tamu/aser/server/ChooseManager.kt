package edu.tamu.aser.server

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.retrox.server.ConnectionManager
import edu.tamu.aser.scheduling.strategy.ThreadInfo
import java.util.*

object ChooseManager {

    fun sendChooseMessageSync(choices: SortedSet<out Any>): Int {
        val index = ConnectionManager.getNewSyncCode()
        val threadInfoDataList = mutableListOf<ThreadInfoData>()
        choices.forEach {
            if (it is ThreadInfo) {
                val data = ThreadInfoData(it.thread.name, it.eventDesc.toString())
                threadInfoDataList.add(data)
            }
        }
        val request = DataTrans(index, "choose", threadInfoDataList)
        val type = object : TypeToken<DataTrans<ThreadInfoData>>() {}.type
        val jsonString = Gson().toJson(request, type)
        val response = ConnectionManager.connectionHandler?.sendMessageBlock(jsonString, index)
                ?: throw IllegalStateException("客户端返回数据错误")

        val typeResponse = object : TypeToken<DataTrans<ThreadChoiceResponse>>() {}.type
        val res: DataTrans<ThreadChoiceResponse> = Gson().fromJson(response, typeResponse)
        return res.data.index
    }

}

data class ThreadInfoData(val thread: String, val event: String)
data class ThreadChoiceResponse(val index: Int)

data class DataTrans<T>(val response_code: Int, val router: String, val data: T)