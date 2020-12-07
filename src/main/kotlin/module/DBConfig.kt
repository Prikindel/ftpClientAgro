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

        enum class BDParams(val value: String) {
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
             * humidity
             *
             */
            HUM,

            /**
             * soil temperature
             *
             */
            SOILTMP
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
                Table.HUM       -> "humidity"
                Table.SOILTMP   -> "soiltemp"
            })
            put("lat", lat)
            put("lng", lng)
            put("value", value)
            put("date", date)
        }
    }
}