/**
 * Возвращает путь к запускаемому проекту
 *
 * @return
 */
fun currentDir1(): String {
    val path = System.getProperty("java.class.path")
    val FileSeparator = System.getProperty("file.separator")
    return path.substring(0, path.lastIndexOf(FileSeparator) + 1)
}

/**
 * Возвращает путь к запускаемому проекту (For testing in IntelliJ IDEA)
 *
 * @return
 */
fun currentDir() = System.getProperty("user.dir")