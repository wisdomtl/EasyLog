package com.taylor.easylog

interface Interceptor<INPUT> {
    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
    fun log(message: INPUT, tag: String, priority: Int, chain: Chain) {}
//
//    /**
//     * print the logs
//     * @return whether terminate the responsibility chain
//     */
//    fun logBatch(vararg messages: INPUT, tag: String, priority: Int, chain: Chain) {}

    /**
     * whether apply [log] logic
     * @return true means apply, otherwise false
     */
    fun enable(): Boolean
}