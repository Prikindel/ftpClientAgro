package parser

import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class ParsingFile {
    private lateinit var file: File

    companion object {
        fun getInstance(filePath: String) = ParsingFile().apply {
            file = File(filePath)
        }
    }

    fun parser() {
        var flagParsing = false
        // Дата для данных файла
        val fileDate = file.name
            .substringBeforeLast('-')
            .filter { it.isDigit() }
            .let {
                "${it[0]}${it[1]}${it[2]}${it[3]}" +
                        "-" +
                        "${it[4]}${it[5]}" +
                        "-" +
                        "${it[6]}${it[7]}"
            }

        file.forEachLine {
            // Проверка на конец типа данных
            if (it.contains(FileConfig.typeData.LON.type)) {
                flagParsing = false
            }
            // парсинг
            if (flagParsing) {
                val lng = it.substringBefore(',')
                val lat = it.substringAfter(',')
                    .substringBefore(',')
                val value = it.substringAfter(',')
                    .substringAfter(',')
                    .substringBefore(',')

                val url = URL("http://stomax.mcdir.ru/setDataFTP.php?lat=$lat&lng=$lng&value=$value&date=$fileDate")
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"

                    //println("\nSent 'POST' request to URL : $url; Response Code : $responseCode")

                    //inputStream.bufferedReader()
                }

                //println("lat = $lat; lng = $lng; value = $value")
            }
            // проверка типа нижележащих данных
            if (it.contains(FileConfig.typeData.TMP.type)) {
                flagParsing = true
            }
        }


    }
}