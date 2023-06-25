package com.zenmen.easylog_su.model

/**
 * An inner model wrap [T] with an unique id
 */
data class Log<T>(val id: String, val data: T)

/**
 * An inner model wrap [T] with a list of unique id
 */
data class LogBatch<T>(val ids: List<String>, val data: T)