package module

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException




class DB {
    private val URL = "jdbc:mysql://a279779.mysql.mchost.ru/a279779_stomax"

    fun test() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val con: Connection = DriverManager.getConnection(URL, DBConfig.USER, DBConfig.PASSWORD)
            val stmt = con.createStatement()
            val rs = stmt.executeQuery("SELECT count(*) FROM coordinates")
            while (rs.next()) {
                println("Count ${rs.getInt(1)}")
            }
            con.close()
            stmt.close()
            rs.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {

        }
        /*try {
            Class.forName("com.mysql.jdbc.Driver")
            val url = "jdbc:mysql://" + DBConfig.HOST + "/" + DBConfig.DATABASE
            val con = DriverManager.getConnection(url, DBConfig.USER, DBConfig.PASSWORD)
            try {
                val stmt = con.createStatement()
                val rs = stmt.executeQuery("SELECT * FROM coordinates")
                while (rs.next()) {
                    val str = rs.getString("id") + ":" + rs.getString(2)
                    println("Contact: $str")
                }
                rs.close()
                stmt.close()
            } finally {
                con.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
    }

    fun setCoordinate(coordinate: Coordinates): Boolean {
        var flagResult = true

        return flagResult
    }

    fun setData(data: Data): Boolean {
        var flagResult = true

        return flagResult
    }
}