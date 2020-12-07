package module

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement


class DB {
    //private val URL = "jdbc:mysql://a279779.mysql.mchost.ru/a279779_stomax"
    private val URL = "jdbc:mysql://${DBConfig.Companion.Test.HOST.value}/${DBConfig.Companion.Test.DATABASE.value}"//?allowMultiQueries=true"
    private lateinit var connection: Connection
    private lateinit var stmt: Statement

    companion object {
        /**
         * Подключение к БД и возврат объекта [DB]
         *
         */
        fun getInstance() = DB().apply {
            try {
                connection = DriverManager.getConnection(URL,
                        DBConfig.Companion.Test.USER.value,
                        DBConfig.Companion.Test.PASSWORD.value
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
                flagReturn = stmt.execute(sql)
                //stmt.close()
            } catch (e: SQLException) {
                e.printStackTrace()
                addCoordinateDataSql(sql, true)
            }
        }
        return flagReturn
    }
}