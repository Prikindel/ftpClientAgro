package module

data class RegionCoordinates(
        val lat: String,
        val lon: String
) {
    override fun toString() = "($lat, $lon)"
}