package presenter

import ftp.FTP
import parser.FileConfig
import parser.ParsingFile
import java.io.File
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

/**
 * Класс выполняет основную работу по работе с данными на FTP сервере и парсингу
 *
 */
class Presenter {
    /**
     * Количество потоков для параллельной обработки данных
     */
    private var flowSize: Int = 1

    /**
     * Клиент FTP
     */
    private lateinit var ftp: FTP

    /**
     * Дирекутория для файлов
     */
    private var directoryFTP = FTP.Companion.Directory.LAST

    /**
     * Семафор для потоков
     */
    private var semaphore = Semaphore(flowSize)

    companion object {
        /**
         * Инициализация объекта Presenter с переданными данными
         *
         * @param directory директория ftp сервера для получения данных
         * @param flow количество потоков
         * @return [Presenter]
         */
        fun getInstance(directory: FTP.Companion.Directory = FTP.Companion.Directory.LAST, flow: Int = 1) = Presenter().apply {
            flowSize = flow
            semaphore = Semaphore(flowSize)
            directoryFTP = directory
            ftp = FTP.getInstance(directory)
        }
    }

    /**
     * Получение списка файлов на FTP сервере в текущей директории
     * Работа производится в фоне, но ожидается окончание получения данных
     *
     * @return список файлов
     */
    fun getList(): List<String> {
        println("Получение списка файлов на FTP сервере...")
        var list = listOf<String>()
        thread(start = true) {
            list = ftp.list()
        }.join()
        println("Получен список файлов: \n ${list.size} файлов \n")
        return list
    }

    /**
     * Скачиваие и парсинг файлов
     *
     * @param list список файлов для парсинга
     */
    fun listParsing(list: List<String>) {
        println("Скачивание файлов на локальную машину и парсинг с последующей отправкой на сервер данных\n")
        list.forEachIndexed { index, it ->
            //if (it.contains("wind-data20201225-17.txt")) {
                thread(start = true) {
                    semaphore.acquire()
                    println("Скачивание файла №${index + 1} $it")
                    ftp.download(it)
                    println("Парсинг файла №${index + 1} $it и отправка данных на сервер")
                    ParsingFile.getInstance("${ftp.DOWNLOAD_DIRECTORY}$it").parser()
                    println("Удаление файла №${index + 1} $it с локальной машины")
                    File("${System.getProperty("user.dir")}/LAST/$it").delete()
                    semaphore.release()
                }
            //}
        }
    }
}