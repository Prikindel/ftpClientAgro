import ftp.FTP
import parser.ParsingFile
import java.io.File
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

fun main() {

    println("Получение списка файлов на FTP сервере...")
    var list = listOf<String>()
    thread(start = true) {
        list = FTP.getInstance(FTP.Companion.Directory.LAST)
            .list()
    }.join()

    println("Получен список файлов: \n ${list.size} файлов \n $list \n")

    println("Скачивание файлов на локальную машину и парсинг с последующей отправкой на сервер данных\n")
    val semaphore = Semaphore(10)
    list.forEachIndexed { index, it ->
        thread(start = true) {
            semaphore.acquire()
            println("Скачиваем файл №${index + 1} $it")
            FTP.getInstance(FTP.Companion.Directory.LAST)
                .download(it)
            println("Парсинг файла №${index + 1} $it и отправка данных на сервер")
            ParsingFile.getInstance("${System.getProperty("user.dir")}/LAST/$it").parser()
            println("Удаление файла №${index + 1} $it с локальной машины")
            File("${System.getProperty("user.dir")}/LAST/$it").delete()
            semaphore.release()
        }
    }

    //println("\nКонец работы программы")

    /*list.forEach {
        thread(start = true) {
            semaphore.acquire()
            ParsingFile.getInstance("${System.getProperty("user.dir")}/LAST/$it").parser()
            File("${System.getProperty("user.dir")}/LAST/$it").delete()
            semaphore.release()
        }
    }*/
}