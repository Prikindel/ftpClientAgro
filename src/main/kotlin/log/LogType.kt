package log

import module.Region
import module.RegionCoordinates

sealed class LogType {
    data class InfoRegionGetData(
            val numberInfo: Int = 1,
            val region: Region,
            val coordinatesThis: RegionCoordinates,
            var message: String = ""
    ) : LogType()

    data class Regions(
            val prefix: String = "Найдено",
            val count: Int = 0,
            val regions: List<Region> = listOf(),
            var message: String = ""
    ) : LogType()

    val log: String
        get() = when(this) {
            is InfoRegionGetData ->
                "Координата №$numberInfo $coordinatesThis,\n" +
                        "\t$region\n" +
                        "\t$message"
            is Regions ->
                "$prefix $count регионов:\n" +
                        "\t${regions}\n" +
                        "\t$message"
        } + "\n"

    override fun toString() = log
}

fun <T : LogType> T.setMessage(message: String) : LogType {
    when(this) {
        is LogType.InfoRegionGetData -> this.message = message
        is LogType.Regions -> this.message = message
    }
    return this
}