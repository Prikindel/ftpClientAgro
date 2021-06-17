package openweather

import com.google.gson.annotations.SerializedName
import com.sun.xml.internal.ws.developer.Serialization

@Serialization
data class OpenWeatherResponse(
        var lat: String,
        var lon: String,
        @SerializedName("timezone_offset")
        val timeZoneOffset: String,
        val hourly: List<Hourly> = listOf()
)

@Serialization
data class Hourly(
        val dt: String,
        val temp: String,
        val pressure: String,
        val humidity: String,
        @SerializedName("wind_speed")
        val windSpeed: String,
        @SerializedName("wind_deg")
        val windDeg: String,
        val rain: Rain? = Rain(),
        val snow: Snow? = Snow()
)

@Serialization
data class Rain(
        @SerializedName("1h")
        val rain: String = "0"
)

@Serialization
data class Snow(
        @SerializedName("1h")
        val snow: String = "0"
)
