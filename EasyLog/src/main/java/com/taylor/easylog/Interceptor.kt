package com.taylor.easylog

interface Interceptor<T> {
    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
    fun log(message: T, tag: String, priority: Int, chain: Chain) {}

    /**
     * print the logs
     * @return whether terminate the responsibility chain
     */
    fun logBatch(vararg messages: T, tag: String, priority: Int, chain: Chain) {}

    /**
     * whether apply [log] logic
     * @return true means apply, otherwise false
     */
    fun enable(): Boolean
}