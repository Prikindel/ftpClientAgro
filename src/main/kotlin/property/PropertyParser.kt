package property

import currentDir
import java.io.File

class PropertyParser {
    companion object {
        val PATH = "${currentDir()}/config.ini"

        fun checkFile() = File(PATH).exists()

        fun createFile() {
            val file = File(PATH)
            if (!file.exists()) {
                file.createNewFile()
            }
        }
    }
}