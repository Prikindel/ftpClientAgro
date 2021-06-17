package presenter

import ftp.FTP
import module.DB
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
    fun getList(): List<RegionCoordinates> {
        println("Получение списка координат из БД...")
        var list = listOf<RegionCoordinates>()
        thread(start = true) {
            list = DB.getMiddleRegionsCoordinates()
        }.join()
        println("Получен список регионов: \n ${list.size} регионов \n")
        return list
                .flatMap {
                    it.toListCloseCoordinates()
                }
    }

    /**
     * Получение и парсинг данных
     *
     * @param list список координат для парсинга
     */
    fun listParsing(list: List<RegionCoordinates>) {
        println("Получение данных для координат и парсинг с последующей отправкой на сервер данных\n")
        list.forEachIndexed { index, it ->
            //if (index == 1)
            thread(start = true) {
                semaphore.acquire()
                println("Получение данных для №${index + 1} $it")
                val response = openWeather.callUrl(it.lat, it.lon)
                println("Парсинг данных №${index + 1} $it и отправка на сервер")
                ParserAPI().parser(response)
                println("Успешно загруженны данные для №${index + 1} $it")
                println()
                semaphore.release()
            }

        }
    }
}