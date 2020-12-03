package module

import java.net.HttpURLConnection
import java.net.URL

/**
 * Выполняет запросы к серверу
 *
 */
class Rest {
    companion object {
        val URL = "http://stomax.mcdir.ru/"
        val DATAFTP = "setDataFTP.php"

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
                Table.TMP       -> "TMP"
                Table.WIND      -> "WIND"
                Table.HUM       -> "HUM"
                Table.SOILTMP   -> "SOILTMP"
            })
            put("lat", lat)
            put("lng", lng)
            put("value", value)
            put("date", date)
        }
    }

    /**
     * Передача данных на сервер для добавления их в БД
     *
     * @param params мап данных
     * @param printFlag выводить ли информацию о данных на экран. По умолчанию false
     */
    fun setDataFtp(params: Map<String, String>, printFlag: Boolean = false) {//= URL("$URL$DATAFTP?${paramsToString(params)}").openConnection()
        val url = URL("$URL$DATAFTP?${paramsToString(params)}")
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            if (printFlag) {
                println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
            } else {
                responseCode
            }
            //inputStream.bufferedReader()
        }
    }

    /**
     * Формирует из мапа данных строку дял GET запроса
     *
     * @param params
     * @return
     */
    private fun paramsToString(params: Map<String, String>): String {
        var str = ""
        params.forEach { key, value ->
            str += "$key=$value&"
        }
        return str
    }
}