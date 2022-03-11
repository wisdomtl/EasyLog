package com.taylor.easylog

interface LogInterceptor {
    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
    fun log(priority: Int, tag: String, log: String)

    /**
     * whether apply [log] logic
     * @return true means apply, otherwise false
     */
    fun enable():Boolean
}