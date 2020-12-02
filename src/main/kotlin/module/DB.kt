package module

import java.lang.Exception
import java.sql.DriverManager
import java.sql.SQLException




class DB {
    fun test() {
        try {
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
        }
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