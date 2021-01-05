package ftp

import it.sauronsoftware.ftp4j.FTPClient
import java.io.File

/**
 * Класс [FTP] предназначен для работы с подключением к серверу, получением и скачиванием данных с FTP сервера
 *
 */
class FTP {
    /**
     * Переменная хранит клиента FTP
     */
    private lateinit var ftpClient: FTPClient

    /**
     * Директория, куда загружаются файлы
     */
    var DOWNLOAD_DIRECTORY = "${System.getProperty("java.class.path")}/LAST/"

    companion object {
        /**
         * К какой директории идет подключение
         *
         */
        enum class Directory {
            /**
             * Директория /LAST
             *
             */
            LAST,

            /**
             * Лиректория /AGO1
             *
             */
            AGO1,

            /**
             * Лиректория /AGO2
             *
             */
            AGO2
        }

        /**
         * Метод генератор - генерирует объект класса [FTP] с параметрами сервера FTP, записанными в [FtpConfig]
         *
         * @param directory Дирректория подключения
         * @return объект класса [FTP]
         */
        fun getInstance(directory: Directory) = FTP().apply {
            ftpClient = FTPClient()
            ftpClient.let {
                it.connect(FtpConfig.HOST, FtpConfig.PORT)
                it.login(FtpConfig.USER, FtpConfig.PASSWORD)
                it.type = FTPClient.TYPE_BINARY
                it.changeDirectory(getDirectory(directory))
                DOWNLOAD_DIRECTORY = System.getProperty("java.class.path") + getDirectory(directory)
            }
        }

        /**
         * Возвращает путь директории
         *
         * @param directory
         * @return путь
         */
        private fun getDirectory(directory: Directory) = when (directory) {
            Directory.LAST -> FtpConfig.DIRECTORYLAST
            Directory.AGO1 -> FtpConfig.DIRECTORYAGO1
            Directory.AGO2 -> FtpConfig.DIRECTORYAGO2
        }
    }

    /**
     * Отключаться от сервера
     *
     */
    fun disconnect() = ftpClient.disconnect(true)

    /**
     * Метод получает с сервера список файлов в текущей директории и возвращает список с названием файлов [List]
     *
     * @return список файлов List<String>
     */
    fun list(): List<String> {
        val list = ArrayList<String>()
        ftpClient.list().forEach {
            list.add(it.name)
        }
        val currentThread = Thread.currentThread()
        return list
    }

    /**
     * Скачивает с сервера файл [file] в директорию [DOWNLOAD_DIRECTORY] на локальной машине
     *
     * @param file Название файла для скачивания
     * @return успех о скачивании
     */
    fun download(file: String): Boolean {
        var resultBoolean = true

        try {
            val folder = File(DOWNLOAD_DIRECTORY)
            if (!folder.exists()) {
                folder.mkdir()
            }
            val localFile = File(DOWNLOAD_DIRECTORY + file)
            localFile.createNewFile()
            ftpClient.download(file, localFile)
        } catch (e: Exception) {
            e.printStackTrace()
            resultBoolean = false
        }

        return resultBoolean
    }
}