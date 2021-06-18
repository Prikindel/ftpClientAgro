package presenter

import ftp.FTP
import log.Log
import log.LogType
import log.setMessage
import module.DB
import module.Region
import module.RegionCoordinates
import openweather.OpenWeather
import parser.ParserAPI
import parser.ParsingFile
import toListCloseCoordinates
import java.io.File
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class ApiToDb {
    /**
     * Количество потоков для параллельной обработки данных
     */
    private var flowSize: Int = 1

    /**
     * API клиент
     */
    private val openWeather = OpenWeather()

    /**
     * Семафор для потоков
     */
    private var semaphore = Semaphore(flowSize)

    private val listThread = mutableListOf<Thread>()

    companion object {
        /**
         * Инициализация объекта Presenter с переданными данными
         *
         * @param flow количество потоков
         * @return
         */
        fun getInstance(flow: Int = 1) = ApiToDb().apply {
            flowSize = flow
            semaphore = Semaphore(flowSize)
        }
    }

    /**
     * Получение списка координат
     *
     * @return список координат
     */
    fun getList(): List<Region> {
        println("Получение списка координат из БД...\n")
        var list = listOf<Region>()
        thread(start = true) {
            list = DB.getMiddleRegionsCoordinates()
        }.join()

        var countCoordinates = 0
        list.forEach {
            countCoordinates += it.closeCoordinates.size
        }

        Log.i(
            LogType.Regions(
                count = list.size,
                regions = list,
                message = "$countCoordinates координат найдено"
            ).log
        )
        return list
    }

    /**
     * Получение и парсинг данных
     *
     * @param list список координат для парсинга
     */
    fun listParsing(list: List<Region>) {
        Log.d("Получение данных для координат и парсинг с последующей отправкой на сервер данных\n")
        var countCoordinates = 0
        list.forEachIndexed { indexRegion, region ->
            region.closeCoordinates.forEachIndexed { indexCoordinate, coordinate ->
//                if (indexCoordinate == 1)
                listThread.add(
                    thread(start = true) {
                        semaphore.acquire()
                        countCoordinates++
                        val logType = LogType.InfoRegionGetData(
                            numberInfo = countCoordinates,
                            region = region,
                            coordinatesThis = coordinate
                        )
                        Log.i("***************************************************")
                        Log.i(logType.setMessage("получение данных из источника").log)
                        val response = openWeather.callUrl(coordinate.lat, coordinate.lon)
                        ParserAPI().parser(response)
                        Log.i(logType.setMessage("данные успешно загружены" +
                                "\n***************************************************").log)
                        semaphore.release()
                    }
                )
            }
        }
        listThread.forEach { it.join() }
    }
}