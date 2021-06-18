import log.Log
import presenter.ApiToDb
import presenter.Presenter
import java.util.*

fun main(args: Array<String>) {
    val flowInt = startMain(args)

    presenterRun(flowInt)

    endMain()
}

fun startMain(args: Array<String>): Int {
    Log.y("\n***************************************************")
    Log.y("Вас приветствует программа парсинга данных погоды")
    Log.y("***************************************************\n")
    var flowInt = 1
    if (args.isNotEmpty() && args[0].all {it.isDigit()}) {
        flowInt = args[0].toInt()
        if (flowInt <= 0) {
            flowInt = 1
        }
    } else {
        while (true) {
            print("Введите количество желаемых одновременно работающих потоков: ")
            val flowSize = readLine()
            if (flowSize.isNullOrEmpty()) {
                println("ERROR. Необходимо ввести количество потоков")
            } else if (flowSize.all { it.isDigit() }) {
                flowInt = flowSize.toInt()
                if (flowInt <= 0) {
                    flowInt = 1
                }
                break
            } else {
                println("ERROR. Введите число")
            }
        }
    }
    return flowInt
}

fun endMain() {
    Log.y("***************************************************")
    Log.y("Благодарим за использование ftpClientAgro")
    Log.y("***************************************************\n")
}

//fun main1(args: Array<String>) {
//    println("Вас приветствует программа парсинга данных с FTP сервера \"Фобос\"")
//    println()
//    if (args.isNotEmpty() && args[0].all {it.isDigit()}) {
//        var flowInt = args[0].toInt()
//        if (flowInt <= 0) {
//            flowInt = 1
//        }
//        val presenter = Presenter.getInstance(flow = flowInt)
//        presenter.listParsing(presenter.getList())
//        println()
//        println("Парсинг закончен. ")
//    } else {
//        while (true) {
//            print("Введите количество желаемых одновременно работающих потоков: ")
//            val flowSize = readLine()
//            if (flowSize.isNullOrEmpty()) {
//                println("ERROR. Необходимо ввести количество потоков")
//            } else if (flowSize.all { it.isDigit() }) {
//                var flowInt = flowSize.toInt()
//                if (flowInt <= 0) {
//                    flowInt = 1
//                }
//                val presenter = Presenter.getInstance(flow = flowInt)
//                presenter.listParsing(presenter.getList())
//                println()
//                println("Парсинг закончен. ")
//                break
//            } else {
//                println("ERROR. Введите число")
//            }
//        }
//    }
//}