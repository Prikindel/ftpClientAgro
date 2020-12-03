package parser

/**
 * Класс Содержит данные о существующих типах данных [FileName] и [TypeData]
 *
 */
class FileConfig {
    companion object {
        /**
         * Проверяет тип данных на существование соответсвующих данных в файлах
         *
         * @param type
         */
        fun isTypeData(type: TypeData) = when (type) {
            TypeData.TMP        -> true
            TypeData.HUM        -> true
            TypeData.SOILTMP    -> true
            TypeData.WIND       -> true
            TypeData.ALL        -> true
            else                -> false
        }
    }
    /**
     * Содержит данные о существующих файлах
     *
     * @property file название начала файла
     */
    enum class FileName(val file: String) {
        /**
         * Содержит такие данные, как:
         *  - температура ([TypeData.TMP])
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
    enum class TypeData(val type: String) {
        /**
         * Все данные
         *
         */
        ALL("ALL"),
        /**
         * Температура
         *
         */
        TMP("TMP"),

        /**
         * Скорость ветра
         *
         */
        WIND("GUST"),

        /**
         * Температура почвы
         *
         */
        SOILTMP("TSOIL 0-0.1 m"),

        /**
         * Влажность воздуха
         *
         */
        HUM("RH"),

        /**
         * Начало каждого нового разделения данных
         *
         */
        LON("lon")
    }
}