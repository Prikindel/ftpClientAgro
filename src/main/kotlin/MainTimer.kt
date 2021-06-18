import log.Log

fun main(args: Array<String>) {
    val flowInt = startMain(args)

    timerRun(flowInt)

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

    endMain()
}