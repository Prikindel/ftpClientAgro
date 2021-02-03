package module

/**
 * Конфигурация базы данных
 *
 */
class DBConfig {
    companion object {

        enum class Test(val value: String) {
            HOST("localhost"),
            DATABASE("a279779_stomax"),
            USER("root"),
            PASSWORD("usbw")
        }

        enum class DBParams(val value: String) {
            HOST("localhost"),
            DATABASE("agro"),
            USER("prike"),
            PASSWORD("FktrcR888")
        }

        /**
         * Типы данных таблиц БД
         *
         */
        enum class Table {
            /**
             * Температура
             *
             */
            TMP,

            /**
             * wind
             *
             */
            WIND,

            /**
             * wdir
             *
             */
            WDIR,

            /**
             * humidity
             *
             */
            HUM,

            /**
             * soil temperature 0 - 0.1 m
             *
             */
            SOILTMP,

            /**
             * soil temperature 0.1 - 0.4 m
             *
             */
            SOILTMP1,

            /**
             * soil temperature 0.4 - 1 m
             *
             */
            SOILTMP2,

            /**
             * soil humidity 0 - 0.1 m
             *
             */
            SOILHUM,

            /**
             * soil humidity 0.1 - 0.4 m
             *
             */
            SOILHUM1,

            /**
             * soil humidity 0.4 - 1 m
             *
             */
            SOILHUM2,

            /**
             * osadki
             *
             */
            OSADKI
        }

        /**
         * Маппинг данных
         *
         * @param table таблица данных для записи в БД
         * @param lat
         * @param lng
         * @param value
         * @param date
         */
        fun dataToSetDataFtp(table: Table, lat: String, lng: String, value: String, date: String) = HashMap<String, String>().apply {
            put("table", when(table) {
                Table.TMP       -> "temperature"
                Table.WIND      -> "wind"
                Table.WDIR      -> "wdir"
                Table.HUM       -> "humidity"
                Table.SOILTMP   -> "soiltemp"
                Table.SOILTMP1  -> "soiltemp1"
                Table.SOILTMP2  -> "soiltemp2"
                Table.SOILHUM   -> "soilhum"
                Table.SOILHUM1  -> "soilhum1"
                Table.SOILHUM2  -> "soilhum2"
                Table.OSADKI    -> "osadki"
            })
            put("lat", lat)
            put("lng", lng)
            put("value", value)
            put("date", date)
        }
    }
}