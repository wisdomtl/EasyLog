package com.taylor.easylog

abstract class Interceptor<T> {

    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
    abstract fun log(tag: String, message: T, priority: Int, chain: Chain, vararg args: Any)

    /**
     * whether apply [log] logic
     * @return true means apply, otherwise false
     */
    var isLoggable: (T) -> Boolean = { true }
}