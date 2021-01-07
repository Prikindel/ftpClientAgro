package property

import currentDir
import java.io.File

class Property {
    companion object {
        /**
         * Путь к файлу конфигурации
         */
        val PATH = "${currentDir()}/config.ini"

        /**
         * Типы данных, хранимых в файле кофигурации
         *
         */
        enum class Types(value: String) {
            /**
             * Путь для временного хранения sql файлов
             *
             */
            FILES("path.files"),

            /**
             * Путь для временного хранения загруженных файлов с данными Фобос
             *
             */
            LAST("path.last"),

            /**
             * Объект данных для парсинга
             *
             */
            DATA("data"),

            /**
             * Объект данных таблицы БД
             *
             */
            TABLE("table"),

            /**
             * Хост БД
             */
            DBHOST("db.host"),

            /**
             * База данных БД
             */
            DBNAME("db.database"),

            /**
             * Пользователь БД
             */
            DBUSER("db.user"),

            /**
             * Пароль БД
             */
            DBPASSWORD("db.password")
        }

        /**
         * Проверка существования файла конфигурации
         *
         */
        fun checkFile() = File(PATH).exists()

        /**
         * Сощдание файла конфигурации с дефолтыми настройками, если такого нет
         *
         */
        fun createFile() {
            val file = File(PATH)
            if (!file.exists()) {
                file.createNewFile()
            }
        }
    }
}