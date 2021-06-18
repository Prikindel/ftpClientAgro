package log

import currentDir
import java.io.File
import java.io.FileOutputStream

object Log {
    private val filename = "log"

    private const val ANSI_RESET = "\u001B[0m"
    private const val ANSI_BLACK = "\u001B[30m"
    private const val ANSI_RED = "\u001B[31m"
    private const val ANSI_GREEN = "\u001B[32m"
    private const val ANSI_YELLOW = "\u001B[33m"
    private const val ANSI_BLUE = "\u001B[34m"
    private const val ANSI_PURPLE = "\u001B[35m"
    private const val ANSI_CYAN = "\u001B[36m"
    private const val ANSI_WHITE = "\u001B[37m"

    private fun printColor(color: String = ANSI_RESET, message: String, toFile: Boolean = true) {
        println(color + message + ANSI_RESET)
        if (toFile) printToFile(message)
    }

    fun p(message: String) =
        printColor(ANSI_PURPLE, message)

    fun e(message: String) =
        printColor(ANSI_RED, message)

    fun i(message: String) =
            printColor(ANSI_GREEN, message)

    fun d(message: String) =
            printColor(message = message, toFile = false)

    fun y(message: String) =
            printColor(ANSI_YELLOW, message, false)

    fun printToFile(message: String) {
        val file = File(currentDir(), filename)
        file.createNewFile()
        FileOutputStream(file, true)
            .bufferedWriter()
            .use {
                it.newLine()
                it.write(message)
            }
    }
}