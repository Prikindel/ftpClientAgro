import log.Log
import module.RegionCoordinates
import presenter.ApiToDb
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Возвращает путь к запускаемому проекту
 *
 * @return
 */
fun currentDir(): String {
    val path = System.getProperty("java.class.path")
    val FileSeparator = System.getProperty("file.separator")
    return path.substring(0, path.lastIndexOf(FileSeparator) + 1)
}

/**
 * Возвращает путь к запускаемому проекту (For testing in IntelliJ IDEA)
 *
 * @return
 */
fun currentDir1() = System.getProperty("user.dir")!!

fun currentDir(folder: String): String {
    val path = "${currentDir()}$folder/"
    val folder = File(path)
    if (!folder.exists()) {
        folder.mkdir()
    }
    return path
}

fun String.toDBCoordinate() = substringBefore('.') + '.' +
        (if (contains('.')) substringAfter('.') else "")
                .padEnd(6, '0')

/**
 * Возвращает список координат, которые находятся вокруг центра регионов
 * и сам центр региона
 */
fun RegionCoordinates.toListCloseCoordinates(): List<RegionCoordinates> {
    val listCoordinates = mutableListOf(this)

    val latFloat = lat.getTwoSymbolFloat()
    val lonFloat = lon.getTwoSymbolFloat()

    val mapLat = latFloat.toListCoordinate()
    val mapLon = lonFloat.toListCoordinate()

    val mapLatLast = mapLat.toLastListCoordinate(lat.getSymbolInt())
    val mapLonLast = mapLon.toLastListCoordinate(lon.getSymbolInt())

    mapLatLast.forEach { lats ->
        mapLonLast.forEach { lons ->
            listCoordinates.add(
                    RegionCoordinates(
                            lats,
                            lons
                    )
            )
        }
    }

    return listCoordinates
}

fun List<Int>.toLastListCoordinate(int: Int) = with(
        if (get(0) == 75 && get(1) == 0)
            listOf("$int.${get(0)}", "${int + 1}.${get(1)}")
        else
            listOf("$int.${get(0)}", "$int.${get(1)}")
) {
    if (get(0) == get(1))
        this.toSet().toList()
    else
        this
}

fun Int.toListCoordinate() = when (this) {
    in 0..24 -> listOf(0, 25)
    in 26..49 -> listOf(25, 50)
    in 51..74 -> listOf(50, 75)
    in 76..99 -> listOf(75, 0)
    else -> listOf(this, this)
}

fun String.getTwoSymbolFloat() = with(this.substringAfter('.')) {
    when (length) {
        0 -> "00"
        1 -> "${get(0)}0"
        else -> "${get(0)}${get(1)}"
    }
}.toInt()

fun String.getSymbolInt() = substringBefore('.').toInt()






// MAIN
fun timerRun(flowInt: Int) {
    Timer(false).scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            presenterRun(flowInt)
        }
    }, 0, 24 * 60 * 60 * 1000)
}

fun presenterRun(flowInt: Int) {
    Log.p("Новый запрос на получение данных " +
        SimpleDateFormat("YYYY-MM-dd HH:00:00")
        .format(
            Date()
        ) + "\n"
    )

    val presenter = ApiToDb.getInstance(flowInt)
    presenter.listParsing(presenter.getList())

    Log.d("Все данные загружены.\n")
}
