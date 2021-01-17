package property

import currentDir
import property.PropertyConfig.Companion.COMMENT
import property.PropertyConfig.Companion.PATH
import property.Types.*
import java.io.File
import java.util.*

object Property {
    private val propertyFile = File(PATH)
    private val prop = Properties()

    init {
        if (createFile()) initDefaultFile()
        loadProperties()
    }

    /**
     * Подгружает данные из файла конфигурации
     *
     */
    fun loadProperties(): Property {
        prop.load(propertyFile.reader())
        return this
    }

    /**
     * Проверка существования файла конфигурации
     *
     * return существует ли файл
     */
    fun checkFile() = propertyFile.exists()

    /**
     * Сощдание файла конфигурации с дефолтыми настройками, если такого нет
     *
     * return создан ли файл
     */
    private fun createFile(): Boolean {
        if (!checkFile()) {
            propertyFile.createNewFile()
            return true
        }
        return false
    }

    /**
     * Инициализация файла конфигурации с дефолтными данными
     *
     */
    fun initDefaultFile(): Property {
        // Очищаем файл
        propertyFile.bufferedWriter().use {it.write("")}

        // Данные для парсинга
        val dataList        = mutableListOf<String>()

        /**
         * Добавляет данные для парсинга в объект с конфигурациями
         *
         * @param typeData тип данных
         * @param fileName файл, в котором находятся данные
         * @param search искомая строка для нижележащих данных
         * @param table таблица с текущими данными в БД
         */
        fun dataToProp(typeData: String, fileName: String, search: String, table: String) {
            dataList.add(typeData)
            prop["$typeData.${FILENAME}"]   = fileName
            prop["$typeData.${SEARCH}"]     = search
            prop["$typeData.${TABLE}"]      = table
        }

        // Данные БД
        prop["$DBHOST"]        = "localhost"
        prop["$DBUSER"]        = "prike"
        prop["$DBPASSWORD"]    = "FktrcR888"
        prop["$DBNAME"]        = "agro"

        // Данные путей
        prop["$LAST"]          = "${currentDir()}/LAST"
        prop["$FILES"]         = "${currentDir()}/FILES"

        // Данные для парсинга
        prop["$DATA"]          = dataList.toString()
        // Температура
        dataToProp(
                typeData = "TMP",
                fileName = "grib",
                search   = ",TMP 2 m above",
                table    = "temperature"
        )
        // Скорость ветра
        dataToProp(
                typeData = "WIND",
                fileName = "wind",
                search   = "WIND 10 m",
                table    = "wind"
        )
        // Влажность воздуха
        dataToProp(
                typeData = "HUM",
                fileName = "grib",
                search   = "RH 2 m above",
                table    = "humidity"
        )
        // Температура почвы на глубине 0-0.1 м
        dataToProp(
                typeData = "SOILTMP",
                fileName = "grib",
                search   = "TSOIL 0-0.1 m",
                table    = "soiltemp"
        )
        // Температура почвы на глубине 0.1-0.4 м
        dataToProp(
                typeData = "SOILTMP1",
                fileName = "grib",
                search   = "TSOIL 0.1-0.4 m",
                table    = "soiltemp1"
        )
        // Температура почвы на глубине 0.4-1 м
        dataToProp(
                typeData = "SOILTMP2",
                fileName = "grib",
                search   = "TSOIL 0.4-1 m",
                table    = "soiltemp2"
        )
        // Направление ветра
        dataToProp(
                typeData = "WDIR",
                fileName = "wdir",
                search   = "WDIR 10 m",
                table    = "wdir"
        )
        // Осадки
        dataToProp(
                typeData = "OSADKI",
                fileName = "osadki",
                search   = "APCP surface",
                table    = "osadki"
        )
        prop["$DATA"]       = dataList.toString()

        writeToFile()

        return this
    }

    /**
     * Запись данных в файл
     *
     * @param clear очистить properties?
     * @return
     */
    fun writeToFile(clear: Boolean = true): Property {
        prop.store(propertyFile.printWriter(), COMMENT)
        if (clear) prop.clear()
        return this
    }

    /**
     * Получить список данных для парсинга
     *
     */
    fun getTypesData() = prop.getProperty(DATA.value, "[]")
            .drop(1)
            .dropLast(1)
            .replace(" ", "")
            .split(",")

    /**
     * Получить имя файла, где хранится переданный тип данных
     *
     * @param type тип данных
     */
    fun getFileNameByTypeData(type: String) = prop.getProperty("$type.$FILENAME", "")

    /**
     * Получить название таблицы БД для переданного типа данных
     *
     * @param type тип данных
     */
    fun getTableByTypeData(type: String) = prop.getProperty("$type.$TABLE", "")

    /**
     * Получить поисковую подстроку для переданного типа данных
     *
     * @param type тип данных
     */
    fun getSearchByTypeData(type: String) = prop.getProperty("$type.$SEARCH", "")

    fun getDBHost() = prop.getProperty("$DBHOST", "localhost")

    fun getDBUser() = prop.getProperty("$DBUSER", "root")

    fun getDBPassword() = prop.getProperty("$DBPASSWORD", "")

    fun getDBName() = prop.getProperty("$DBNAME", "agro")

    fun getLastPath() = prop.getProperty("$LAST", "${currentDir()}/LAST")

    fun getFilesPath() = prop.getProperty("$FILES", "${currentDir()}/FILES")
}