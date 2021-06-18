package log

import module.RegionCoordinates
import toLog

sealed class LogType {
    data class InfoRegionGetData(
            val numberInfo: Int = 1,
            val regionName: String = "",
            val regionCoordinates: RegionCoordinates,
            val coordinatesThis: RegionCoordinates,
            val message: String = ""
    ) : LogType()

    data class Regions(
            val prefix: String = "Найдено",
            val count: Int = 0,
            val regions: Map<String, RegionCoordinates> = mapOf(),
            val message: String = ""
    ) : LogType()

    val log: String
        get() = when(this) {
            is InfoRegionGetData ->
                "Координата №$numberInfo $coordinatesThis,\n" +
                        "\tрегион \"$regionName\" $regionCoordinates\n" +
                        "\t$message"
            is Regions ->
                "$prefix $count регионов:\n" +
                        "\t${regions.toLog()}\n" +
                        "\t$message"
        } + "\n"
}
