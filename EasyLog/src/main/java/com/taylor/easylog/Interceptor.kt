package com.taylor.easylog

abstract class Interceptor<T> {
    internal open var tag: String? = null

    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
    abstract fun log(tag: String, message: T, priority: Int, chain: Chain, vararg args: Any)


    /**
     * whether apply [log] logic
     * @return true means apply, otherwise false
     */
    abstract fun enable(): Boolean
}