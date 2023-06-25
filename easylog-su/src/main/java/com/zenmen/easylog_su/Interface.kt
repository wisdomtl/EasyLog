package com.zenmen.easylog_su


interface Batcher<LOG, LOGS> {

    /**
     * Convert [LOG] into [ByteArray] used by localization
     */
    fun toByteArray(log:LOG): ByteArray

    /**
     * Pack [LOG] into [LOGS] which is a bundle of [LOG]
     */
    fun pack(logs: List<LOG>): LOGS

    /**
     * Upload [LOGS] to server
     */
    suspend fun upload(logBatch: LOGS): Boolean
}