package org.example

sealed class Result
data class Success(val data: Any) : Result()
data class Error(val message: String) : Result()

fun handleResult(result: Result) {
    when(result) {
        is Success -> println("Success: ${result.data}")
        is Error -> println("Error: ${result.message}")
    }
}

@JvmInline
value class Meter(private val value: Double) {
    fun toKilometer() = value / 1000
}

fun main(){

    val length = Meter(1000.0)
    println(length.toKilometer())


    handleResult(Success(200))
    handleResult(Error("Error occurred"))
}