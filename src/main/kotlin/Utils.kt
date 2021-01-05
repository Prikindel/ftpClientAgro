fun Any.currentDir(): String {
    val path = System.getProperty("java.class.path")
    val FileSeparator = System.getProperty("file.separator")
    return path.substring(0, path.lastIndexOf(FileSeparator) + 1)
}