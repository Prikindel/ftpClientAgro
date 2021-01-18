package parser

import currentDir
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
            val folder = File("${currentDir()}/FILES/")
            if (!folder.exists()) {
                folder.mkdir()
            }
        }
    }

    fun getTypeFile() = when {
        file.name.contains(FileConfig.FileName.GRIB.file)   -> FileConfig.FileName.GRIB
        file.name.contains(FileConfig.FileName.OSADKI.file) -> FileConfig.FileName.OSADKI
        file.name.contains(FileConfig.FileName.WDIR.file)   -> FileConfig.FileName.WDIR
        file.name.contains(FileConfig.FileName.WIND.file)   -> FileConfig.FileName.WIND
        else                                                -> null
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


            val minLat = "40.00000"
            val maxLat = "70.00000"
            val minLng = "25.00000"
            val maxLng = "71.00000"

            val fileOut = File("${currentDir()}/FILES/${file.name}")
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
                                if (lat in minLat..maxLat && lng in minLng..maxLng) {
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
                                }
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
                        if (typeData != FileConfig.TypeData.OSADKI && it.contains(typeData.type)) {
                            flagParsing = true
                            typeParsingData = typeData
                        } else if (getTypeFile() == FileConfig.FileName.OSADKI && typeData == FileConfig.TypeData.OSADKI) {
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
            db.toDbByFile("${currentDir()}/FILES/${file.name}")
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
        str.contains(FileConfig.TypeData.WDIR.type)     -> FileConfig.TypeData.WDIR
        str.contains(FileConfig.TypeData.HUM.type)      -> FileConfig.TypeData.HUM
        getTypeFile() == FileConfig.FileName.OSADKI
                && str.contains(FileConfig.TypeData.OSADKI.type)
                                                        -> FileConfig.TypeData.OSADKI
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
        FileConfig.TypeData.WDIR        -> DBConfig.Companion.Table.WDIR
        FileConfig.TypeData.WIND        -> DBConfig.Companion.Table.WIND
        FileConfig.TypeData.OSADKI      -> DBConfig.Companion.Table.OSADKI
        else                            -> null
    }
}