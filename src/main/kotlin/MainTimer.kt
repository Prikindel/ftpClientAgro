fun main(args: Array<String>) {
    println("Вас приветствует программа парсинга данных погоды")
    println()
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

    presenterRun(flowInt)
    //timerRun(flowInt)

    /*while (true) {
        println("Выбирите: \n" +
                "1 - использовать таймер для повторного запуска через сутки \n" +
                "2 - однократное выполнение программы \n" +
                "0 - выход")
        val menu = readLine()
        when (menu) {
            "1" -> timerRun(flowInt)
            "2" -> presenterRun(flowInt)
            "0" -> break
            else -> continue
        }
        break
    }*/

    println("***************************************************")
    println("Благодарим за использование ftpClientAgro")
    println("***************************************************")
}