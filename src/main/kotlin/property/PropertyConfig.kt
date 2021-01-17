package property

import currentDir

class PropertyConfig {
    companion object {
        /**
         * Путь к файлу конфигурации
         */
        val PATH = "${currentDir()}/config.ini"

        /**
         * комментарий к файлу конфигурации
         */
        val COMMENT = ""
    }
}