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
            TypeData.SOILTMP1   -> true
            TypeData.SOILTMP2   -> true
            TypeData.SOILTMP3   -> true
            TypeData.WIND       -> true
            TypeData.WDIR       -> true
            TypeData.OSADKI     -> true
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
        TMP(",TMP 2 m above"),

        /**
         * Скорость ветра
         *
         */
        WIND("WIND 10 m"),

        /**
         * Направление ветра
         *
         */
        WDIR("WDIR 10 m"),

        /**
         * Температура почвы на глубине 0-0.1 м
         *
         */
        SOILTMP("TSOIL 0-0.1 m"),

        /**
         * Температура почвы на глубине 0.1-0.4 м
         *
         */
        SOILTMP1("TSOIL 0.1-0.4 m"),

        /**
         * Температура почвы на глубине 0.4-1 м
         *
         */
        SOILTMP2("TSOIL 0.4-1 m"),

        /**
         * Температура почвы на глубине 1-2 м
         *
         */
        SOILTMP3("TSOIL 1-2 m"),

        /**
         * Влажность воздуха
         *
         */
        HUM("RH 2 m above"),

        /**
         * Осадки
         * начало строки данных
         *
         */
        OSADKI("APCP surface"),

        /**
         * Начало каждого нового разделения данных
         *
         */
        LON("lon"),
        LAT("lat")
    }
}