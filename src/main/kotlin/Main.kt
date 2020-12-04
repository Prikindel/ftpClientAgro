import ftp.FTP
import module.DB
import parser.ParsingFile
import presenter.Presenter
import java.io.File
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    /*println("Вас приветствует программа парсинга данных с FTP сервера \"Фобос\"")
    println()
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
            break
        } else {
            println("ERROR. Введите число")
        }
    }*/
    DB().test()
}