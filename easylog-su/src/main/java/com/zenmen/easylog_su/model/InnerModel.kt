package com.zenmen.easylog_su.model

data class Log(val id: String, val converter: Converter)

class LogBatch private constructor(val logs: List<Log>) {
    class Builder {
        private val logs = mutableListOf<Log>()
        fun addLog(log: Log): Builder {
            logs.add(log)
            return this
        }

        fun addLogs(logs: List<Log>): Builder {
            this.logs.addAll(logs)
            return this
        }

        fun build(): LogBatch {
            return LogBatch(logs)
        }
    }
}

interface Converter {
    fun pack(): ByteArray

    fun unpack():
}