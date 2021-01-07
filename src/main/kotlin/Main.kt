import ftp.FTP
import module.DB
import parser.ParsingFile
import presenter.Presenter
import java.io.File
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread
import java.io.InputStreamReader

import java.io.BufferedReader
import java.lang.Exception
import java.util.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    /*println("Вас приветствует программа парсинга данных с FTP сервера \"Фобос\"")
    println()
    if (args.isNotEmpty() && args[0].all {it.isDigit()}) {
        var flowInt = args[0].toInt()
        if (flowInt <= 0) {
            flowInt = 1
        }
        val presenter = Presenter.getInstance(flow = flowInt)
        presenter.listParsing(presenter.getList())
        println()
        println("Парсинг закончен. ")
    } else {
        while (true) {
            print("Введите количество желаемых одновременно работающих потоков: ")
            val flowSize = readLine()
            if (flowSize.isNullOrEmpty()) {
                println("ERROR. Необходимо ввести количество потоков")
            } else if (flowSize.all { it.isDigit() }) {
                var flowInt = flowSize.toInt()
                if (flowInt <= 0) {
                    flowInt = 1
                }
                val presenter = Presenter.getInstance(flow = flowInt)
                presenter.listParsing(presenter.getList())
                println()
                println("Парсинг закончен. ")
                break
            } else {
                println("ERROR. Введите число")
            }
        }
    }*/

    val prop = Properties()
    /*try {
        prop.put("TEST", listOf(1, 2, 3, 4, 5).toString())
        prop.store(File("${currentDir()}/t.txt").printWriter(), null)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
    }*/
    try {
        prop.load(File("${currentDir()}/t.txt").reader())
        println(prop.stringPropertyNames())
        println(prop.propertyNames().toList().get(1))
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
    }
}