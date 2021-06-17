package openweather

data class OpenWeatherConfig(
        var url: String = "https://api.openweathermap.org/data/2.5/onecall?",
        var units: String = "metric",
        var lang: String = "ru",
        var appid: String = "de87b49a1fda55ebe99eed0da4879c71",
        var lat: String = "",
        var lon: String = "",
        var exclude: String = "minutely,current,daily"
        ) {

        fun url(lat: String = this.lat, lon: String = this.lon) =
                "${url}appid=${appid}&units=${units}&lang=${lang}&lat=${lat}&lon=${lon}&exclude=${exclude}"

        companion object {
                fun test() = OpenWeatherConfig(lat = "55.462610", lon = "37.220274")
        }
}
