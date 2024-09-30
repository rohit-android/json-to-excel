package com.rohit.machinetask

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()

    val result1 = async { apiCall1() }
    val result2 = async { apiCall2() }

    println(result2.await())

    println("${result1.await()} && ${result2.await()}")

    val endTime = System.currentTimeMillis()
    println("Total time taken: ${endTime - startTime} ms")
}

suspend fun apiCall1() : String{
    delay(5000)
    return "Time 5 Seconds"
}
suspend fun apiCall2() : String{
    delay(10000)
    return "Time 10 Seconds"
}