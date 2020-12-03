package parser

import module.Rest
import java.io.File
import java.lang.Exception
import kotlin.concurrent.thread

/**
 * Парсер указанного файла
 *
 */
class ParsingFile {
    private lateinit var file: File
    private var typeParsingData: FileConfig.TypeData? = null

    companion object {
        /**
         * Инициализация
         *
         * @param filePath путь к файлу
         */
        fun getInstance(filePath: String) = ParsingFile().apply {
            file = File(filePath)
        }
    }

    /**
     * Парсинг файла на данные и передача их на сервер
     *
     * @param typeData тип данных для парсинга. По умолчанию [FileConfig.TypeData.ALL]
     * @param printFlag выводить ли информацию о данных на экран. По умолчанию false
     */
    fun parser(typeData: FileConfig.TypeData = FileConfig.TypeData.ALL, printFlag: Boolean = false) {
        if (FileConfig.isTypeData(typeData)) {
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
                if (it.contains(FileConfig.TypeData.LON.type)) {
                    flagParsing = false
                }
                // парсинг
                if (flagParsing && typeParsingData != null) {
                    val lng = it.substringBefore(',')
                    val lat = it.substringAfter(',')
                            .substringBefore(',')
                    val value = it.substringAfter(',')
                            .substringAfter(',')
                            .substringBefore(',')

                    // Передача на сервер
                    try {
                        Rest().setDataFtp(
                                Rest.dataToSetDataFtp(
                                        typoDataToRestTable()!!,
                                        lat,
                                        lng,
                                        value,
                                        fileDate
                                ),
                                printFlag
                        )
                    } catch (e: Exception) {
                        Thread.sleep(2*60*1000)
                        Rest().setDataFtp(
                                Rest.dataToSetDataFtp(
                                        typoDataToRestTable()!!,
                                        lat,
                                        lng,
                                        value,
                                        fileDate
                                ),
                                printFlag
                        )
                    }
                }
                // проверка типа нижележащих данных
                if (!flagParsing) {
                    if (typeData == FileConfig.TypeData.ALL) {
                        typeParsingData = parserStringByType(it)
                        if (typeParsingData != null) flagParsing = true
                    } else {
                        if (it.contains(typeData.type)) {
                            flagParsing = true
                            typeParsingData = typeData
                        }
                    }
                    if (typeParsingData != null) {
                        println("${typeParsingData?.type} type parsing")
                    }
                }
            }
        } else {
            println("Неверно переданный тип данных: ${typeData.name}")
        }
    }

    /**
     * Парсит строку на тип нижележащих данных в файле
     *
     * @param str строка
     * @return тип данных
     */
    private fun parserStringByType(str: String) = when {
        str.contains(FileConfig.TypeData.WIND.type)     -> FileConfig.TypeData.WIND
        str.contains(FileConfig.TypeData.TMP.type)      -> FileConfig.TypeData.TMP
        str.contains(FileConfig.TypeData.SOILTMP.type)  -> FileConfig.TypeData.SOILTMP
        str.contains(FileConfig.TypeData.HUM.type)      -> FileConfig.TypeData.HUM
        else                                            -> null
    }

    /**
     * Перевод типа данных в тип таблицы
     *
     * @param type
     * @return
     */
    private fun typoDataToRestTable() = when (typeParsingData) {
        FileConfig.TypeData.TMP     -> Rest.Companion.Table.TMP
        FileConfig.TypeData.HUM     -> Rest.Companion.Table.HUM
        FileConfig.TypeData.SOILTMP -> Rest.Companion.Table.SOILTMP
        FileConfig.TypeData.WIND    -> Rest.Companion.Table.WIND
        else                        -> null
    }
}