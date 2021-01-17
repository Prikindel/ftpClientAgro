import property.Property


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

    //val prop = Properties()
    /*try {
        prop.put("TEST", listOf(1, 2, 3, 4, 5).toString())
        prop.store(File("${currentDir()}/t.txt").printWriter(), null)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
    }*/
    /*try {
        prop.load(File("${currentDir()}/t.txt").reader())
        println(prop.stringPropertyNames())
        println(prop.propertyNames().toList().get(0))
        println(prop.getProperty("TEST", "[]").drop(1).dropLast(1).split(", ").get(0))
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
    }*/

    Property.initDefaultFile().loadProperties()
    val list = Property.getTypesData()
    println(list)
    println(list[0])
    println(Property.getFileNameByTypeData(list[0]))
    println(Property.getTableByTypeData(list[0]))
    println(Property.getSearchByTypeData(list[0]))
}