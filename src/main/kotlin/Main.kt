package org.example

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.*

interface TaskCallBack<T> {
    fun onTaskComplete(result: T)
    fun onTaskError(e: Exception)
}

suspend fun simulateAsyncTask(): String {
    delay(2000)
    return "Task Completed"
}

class MyAsyncTask<T> {
    @OptIn(DelicateCoroutinesApi::class)
    fun execute(callBack: TaskCallBack<T>) {
        GlobalScope.launch {
            try {
                val result = simulateAsyncTask()
                callBack.onTaskComplete(result as T)
            } catch (e: Exception) {
                callBack.onTaskError(e)
            }
        }
    }
}

fun main() {
    val asyncTask = MyAsyncTask<String>()

    asyncTask.execute(object: TaskCallBack<String> {
        override fun onTaskComplete(result: String) {
            println("MAIN: $result")
        }

        override fun onTaskError(e: Exception) {
            println(e.message)
        }

    })

    runBlocking {
        println("BEFORE DELAY")
        delay(3000)
        println("AFTER DELAY")
    }
}

fun partitionFile(){
    val directoryPath = "/Users/jtepjuk/Downloads/ScreenShots"

    val directory = File(directoryPath)

    val files = directory.listFiles() ?: emptyArray()

    files.forEach { file ->
        val modifiedDate = Date(file.lastModified())
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(modifiedDate)

        val monthDirectory = File("$directory/$month")

        if(!monthDirectory.exists()) {
            monthDirectory.mkdir()
        }

        file.copyTo(File(monthDirectory, file.name))
        file.delete()
    }
}