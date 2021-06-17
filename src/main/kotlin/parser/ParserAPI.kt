package parser

import currentDir
import module.DB
import module.DBConfig
import openweather.OpenWeatherResponse
import toDBCoordinate
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ParserAPI {

    /**
     * Парсинг файла на данные и передача их на сервер
     *
     * @param typeData тип данных для парсинга. По умолчанию [FileConfig.TypeData.ALL]
     * @param printFlag выводить ли информацию о данных на экран. По умолчанию false
     */
    fun parser(response: OpenWeatherResponse) {
        val db = DB.getInstance()
        val FILES = currentDir("FILES")
        val fileName = "$FILES${response.lat}-${response.lon}.sql"
        response.lat = response.lat.toDBCoordinate()
        response.lon = response.lon.toDBCoordinate()
        val fileOut = File(fileName)
        fileOut.createNewFile()
        fileOut.printWriter().use { out ->
            response.hourly.forEach { hourly ->

                fun writeToFile(type: DBConfig.Companion.Table, value: String) {
                    out.println(
                            DB.getSqlAdd(
                                    DBConfig.dataToSetDataFtp(
                                            type,
                                            response.lat,
                                            response.lon,
                                            value,
                                            SimpleDateFormat("YYYY-MM-dd HH:00:00")
                                                    .format(
                                                            Date(hourly.dt.toLong() * 1000)
                                                    )
                                    )
                            )
                    )
                }

                val rain = hourly.rain?.rain ?: hourly.snow?.snow ?: "0"

                writeToFile(DBConfig.Companion.Table.TMP, hourly.temp)
                writeToFile(DBConfig.Companion.Table.HUM, hourly.humidity)
                writeToFile(DBConfig.Companion.Table.WIND, hourly.windSpeed)
                writeToFile(DBConfig.Companion.Table.WDIR, hourly.windDeg)
                writeToFile(DBConfig.Companion.Table.OSADKI, rain)
            }
        }
        db.toDbByFile(fileName)
        db.disconnect()
    }
}