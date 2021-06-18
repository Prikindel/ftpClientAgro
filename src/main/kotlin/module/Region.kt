package module

import toListCloseCoordinates

data class Region(
    val id: String,
    val name: String,
    val coordinates: RegionCoordinates,
    var closeCoordinates: List<RegionCoordinates> = listOf()
) {
    init {
        closeCoordinates = coordinates.toListCloseCoordinates()
    }
    override fun toString() = "регион \"$name\" $coordinates"
}