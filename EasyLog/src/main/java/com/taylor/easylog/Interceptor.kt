package com.taylor.easylog

interface Interceptor<T> {

    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
     fun log(tag: String, message: T, priority: Int, chain: Chain, vararg args: Any)


    /**
     * whether apply [log] logic
     * @return true means apply, otherwise false
     */
     fun enable(): Boolean
}