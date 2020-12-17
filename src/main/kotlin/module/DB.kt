package module

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement


class DB {
    //private val URL = "jdbc:mysql://a279779.mysql.mchost.ru/a279779_stomax"
    private val URL = "jdbc:mysql://${DBConfig.Companion.DBParams.HOST.value}/${DBConfig.Companion.DBParams.DATABASE.value}"//?allowMultiQueries=true"
    private lateinit var connection: Connection
    private lateinit var stmt: Statement

    companion object {
        /**
         * Подключение к БД и возврат объекта [DB]
         *
         */
        fun getInstance() = DB().apply {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(URL,
                        DBConfig.Companion.DBParams.USER.value,
                        DBConfig.Companion.DBParams.PASSWORD.value
                )
                stmt = connection.createStatement()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getSqlAddCD(params: Map<String, String>) = "CALL addCD(" +
                "${params["lat"]}, " +
                "${params["lng"]}, " +
                "${params["value"]}, " +
                "'${params["date"]}', " +
                "'${params["table"]}'" +
                ");"

        fun getSqlAdd(params: Map<String, String>) = "INSERT INTO coordinates (lat, lng) VALUES('${params["lat"]}', '${params["lng"]}') ON duplicate KEY UPDATE id = last_insert_id(id);" +
                "INSERT INTO ${params["table"]} (coordinates, value, date) VALUES(last_insert_id(), '${params["value"]}', '${params["date"]}') ON duplicate KEY UPDATE value = '${params["value"]}';"
    }

    /**
     * Отключается от БД
     *
     */
    fun disconnect() {
        connection.close()
        stmt.close()
    }

    fun test() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val con: Connection = DriverManager.getConnection(URL, "root", "usbw")
            val stmt = con.createStatement()
            val rs = stmt.executeQuery("SELECT 1; SELECT 2; SELECT 3")
            while (rs.next())
                println(rs.getInt(1))
            con.close()
            stmt.close()
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {

        }
    }

    /**
     * Отправляет данные в БД о координате и новых данных с сервера
     *
     * @param params параметры передачиданных ([DBConfig.dataToSetDataFtp])
     * @param flagError флаг для повторной обработки запроса при ошибке
     * @return успех операции
     */
    fun addCoordinateData(params: Map<String, String>, flagError: Boolean = false): Boolean {
        var flagReturn = false
        if (!flagError) {
            val sql = "CALL addCD(" +
                    "${params["lat"]}, " +
                    "${params["lng"]}, " +
                    "${params["value"]}, " +
                    "'${params["date"]}', " +
                    "'${params["table"]}'" +
                    ");"
            //println(sql)
            try {
                //val stmt = connection.createStatement()
                flagReturn = stmt.execute(sql)
                //stmt.close()
            } catch (e: SQLException) {
                e.printStackTrace()
                addCoordinateData(params, true)
            }
        }
        return flagReturn
    }

    /**
     * Выполняет запрос, переданный параметро [sql]
     *
     * @param sql запрос
     * @param flagError
     * @return
     */
    fun addCoordinateDataSql(sql: String, flagError: Boolean = false): Boolean {
        var flagReturn = false
        if (!flagError) {
            try {
                //val stmt = connection.createStatement()
                //flagReturn = stmt.execute(sql)
                connection.prepareStatement(sql).execute()
                //stmt.close()
            } catch (e: SQLException) {
                e.printStackTrace()
                addCoordinateDataSql(sql, true)
            }
        }
        return flagReturn
    }

    fun toDbByFile(file: String) {
        try {
            var line: String?
            /*val r = Runtime.getRuntime()
            r.exec(arrayOf("bash", "-l", "-c", "mysql -u ${DBConfig.Companion.DBParams.USER.value} -p ${DBConfig.Companion.DBParams.DATABASE.value} < $file"), null)
            val p2 = r.exec(arrayOf("bash", "-l", "-c", "${DBConfig.Companion.DBParams.PASSWORD.value}\n"), null)
            val input = BufferedReader(InputStreamReader(p2.inputStream))*/

            val p = Runtime.getRuntime().exec(arrayOf("bash", "-l", "-c", "mysql ${DBConfig.Companion.DBParams.DATABASE.value} < $file"), null)
            val input = BufferedReader(InputStreamReader(p.inputStream))
            while (input.readLine().also { line = it } != null) {
                println(line)
            }
            input.close()
            File(file).delete()
        } catch (err: Exception) {
            err.printStackTrace()
        }
    }
}