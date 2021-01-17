package property

/**
 * Типы данных, хранимых в файле кофигурации
 *
 */
enum class Types(val value: String) {
    /**
     * Путь для временного хранения sql файлов
     *
     */
    FILES("path.files"),

    /**
     * Путь для временного хранения загруженных файлов с данными Фобос
     *
     */
    LAST("path.last"),

    /**
     * Массив с типами данных для парсинга
     *
     */
    DATA("data"),

    /**
     * Искомая подстрока нижележащих данных
     *
     */
    SEARCH("search"),

    /**
     * Название файла, в котором лежат данные
     *
     */
    FILENAME("filename"),

    /**
     * Объект данных таблицы БД
     *
     */
    TABLE("table"),

    /**
     * Хост БД
     */
    DBHOST("db.host"),

    /**
     * База данных БД
     */
    DBNAME("db.database"),

    /**
     * Пользователь БД
     */
    DBUSER("db.user"),

    /**
     * Пароль БД
     */
    DBPASSWORD("db.password");

    override fun toString() = value
}