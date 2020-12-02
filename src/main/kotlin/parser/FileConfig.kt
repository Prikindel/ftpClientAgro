package parser

/**
 * Класс Содержит данные о существующих типах данных [fileName] и [typeData]
 *
 */
class FileConfig {
    /**
     * Содержит данные о существующих файлах
     *
     * @property file название начала файла
     */
    enum class fileName(val file: String) {
        /**
         * Содержит такие данные, как:
         *  - температура ([typeData.TMP])
         *
         */
        GRIB("grib"),
        OSADKI("osadki"),
        WDIR("wdir"),
        WIND("wind")
    }

    /**
     * Содержит данные о существующих типах данных в файлах
     *
     * @property type тип данных
     */
    enum class typeData(val type: String) {
        /**
         * Температура
         *
         */
        TMP("TMP"),

        /**
         * Начало каждого нового разделения данных
         *
         */
        LON("lon")
    }
}