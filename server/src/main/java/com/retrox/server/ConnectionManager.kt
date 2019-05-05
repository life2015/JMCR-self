package com.retrox.server

object ConnectionManager {
    var connectionHandler: ConnectionHandler? = null

    private var sync_index = 0

    fun getNewSyncCode(): Int {
        if (sync_index >= Int.MAX_VALUE) {
            sync_index = 0
        }
        sync_index++
        return sync_index
    }

}