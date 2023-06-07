package com.taylor.easylog

/**
 * print method call stack
 */
fun getCallStack(blackList: List<String>): List<String> {
    return Thread.currentThread()
        .stackTrace.drop(3)
        .filter { it.className !in blackList }
        .map { "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})" }
}


/**
 * Print [Iterable] POJO in which you interested defined by [map]
 */
fun <T> Iterable<T>.log(map: (T) -> String) =
    StringBuilder("[").also { sb ->
        this.forEach { e -> sb.append("\t${map(e)},") }
        sb.append("]")
    }.toString()


/**
 * Print [Map]
 * @param space
 */
fun <K, V> Map<K, V?>.log(space: Int = 0): String {
    val indent = StringBuilder().apply {
        repeat(space) { append(" ") }
    }.toString()
    return StringBuilder("\n${indent}{").also { sb ->
        this.iterator().forEach { entry ->
            val value = entry.value.let { v ->
                (v as? Map<*, *>)?.log("${indent}${entry.key} = ".length) ?: v.toString()
            }
            sb.append("\n\t${indent}[${entry.key}] = $value,")
        }
        sb.append("\n${indent}}")
    }.toString()
}

