package com.taylor.easylog

/**
 * print method call stack
 */
fun getCallStack(blackList: List<String>): String {
    return Thread.currentThread().stackTrace.drop(3).filter { it.className !in blackList }
        .print { "${it.className}.${it.methodName} (line ${it.lineNumber})" }
}

/**
 * print collection bean in which you interested defined by [map]
 */
fun <T> Collection<T>.print(map: (T) -> String) =
    StringBuilder("\n[").also { sb ->
        this.forEach { e -> sb.append("\n\t${map(e)},") }
        sb.append("\n]")
    }.toString()

