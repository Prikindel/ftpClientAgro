package parser

import module.DB
import module.DBConfig
import java.io.File

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
            val folder = File("${System.getProperty("user.dir")}/FILES/")
            if (!folder.exists()) {
                folder.mkdir()
            }
        }
    }

    /**
     * Парсинг файла на данные и передача их на сервер
     *
     * @param typeData тип данных для парсинга. По умолчанию [FileConfig.TypeData.ALL]
     * @param printFlag выводить ли информацию о данных на экран. По умолчанию false
     */
    fun parser(typeData: FileConfig.TypeData = FileConfig.TypeData.ALL, printFlag: Boolean = false) {
        val db = DB.getInstance()
        if (FileConfig.isTypeData(typeData)) {
            //val db = DB.getInstance()
            var flagParsing = false
            // Дата для данных файла
            val fileDate = file.name
                    .substringBeforeLast('.')
                    .filter { it.isDigit() }
                    .let {
                        "${it[0]}${it[1]}${it[2]}${it[3]}" +
                                "-" +
                                "${it[4]}${it[5]}" +
                                "-" +
                                "${it[6]}${it[7]}" +
                                " " +
                                "${it[8]}${it[9]}:00:00"
                    }

            /*var sql = ""
            val limit = 500
            var index = 0*/
            val fileOut = File("${System.getProperty("user.dir")}/FILES/${file.name}")
            fileOut.createNewFile()
            fileOut.printWriter().use { out ->
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

                    /*sql += DB.getSqlAddCD(DBConfig.dataToSetDataFtp(
                            typoDataToRestTable()!!,
                            lat,
                            lng,
                            value,
                            fileDate
                    ))
                    index++*/

                    // Передача на сервер
                    //if (index == limit) {
                        try {
                            /*toDB(db,
                                DB.getSqlAdd(
                                    DBConfig.dataToSetDataFtp(
                                        typoDataToRestTable()!!,
                                        lat,
                                        lng,
                                        value,
                                        fileDate
                                    )
                                )
                            )*/
                            out.println(
                                DB.getSqlAdd(
                                    DBConfig.dataToSetDataFtp(
                                        typoDataToRestTable()!!,
                                        lat,
                                        lng,
                                        value,
                                        fileDate
                                    )
                                )
                            )
                            /*if (db.addCoordinateData(
                                            DBConfig.dataToSetDataFtp(
                                                    typoDataToRestTable()!!,
                                                    lat,
                                                    lng,
                                                    value,
                                                    fileDate
                                            )
                                    )
                                    && printFlag
                            )
                                println("${file.name} - data type is ${typeParsingData?.name}: lat = $lat; lng = $lng; value = $value; date = $fileDate;")*/
                            //db.disconnect()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        //index = 0
                        //sql = ""
                    }
                //}
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
                        println("${file.name} - ${typeParsingData?.name} type parsing")
                    }
                }
            }
            }
            db.toDbByFile("${System.getProperty("user.dir")}/FILES/${file.name}")
            /*if (index != 0) {
                try {
                    toDB(db, sql)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                index = 0
                sql = ""
            }*/
        } else {
            println("Неверно переданный тип данных: ${typeData.name}")
        }
        db.disconnect()
    }

    private fun toDB(db: DB, sql: String) = db.addCoordinateDataSql(sql)

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
        str.contains(FileConfig.TypeData.SOILTMP1.type) -> FileConfig.TypeData.SOILTMP1
        str.contains(FileConfig.TypeData.SOILTMP2.type) -> FileConfig.TypeData.SOILTMP2
        str.contains(FileConfig.TypeData.SOILTMP3.type) -> FileConfig.TypeData.SOILTMP3
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
        FileConfig.TypeData.TMP         -> DBConfig.Companion.Table.TMP
        FileConfig.TypeData.HUM         -> DBConfig.Companion.Table.HUM
        FileConfig.TypeData.SOILTMP     -> DBConfig.Companion.Table.SOILTMP
        FileConfig.TypeData.SOILTMP1    -> DBConfig.Companion.Table.SOILTMP1
        FileConfig.TypeData.SOILTMP2    -> DBConfig.Companion.Table.SOILTMP2
        FileConfig.TypeData.SOILTMP3    -> DBConfig.Companion.Table.SOILTMP3
        FileConfig.TypeData.WIND        -> DBConfig.Companion.Table.WIND
        else                            -> null
    }
}