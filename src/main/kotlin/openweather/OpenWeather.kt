package openweather

import com.google.gson.Gson
import log.Log
import java.net.HttpURLConnection
import java.net.URL

class OpenWeather {
    private var url: String = OpenWeatherConfig().url()

    fun callUrl(lat: String = "", lon: String = ""): OpenWeatherResponse {
        url = OpenWeatherConfig().url(lat, lon)
        return with(URL(url)
                .openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            if (true) {
                Log.p("\nSent 'GET' request to URL : $url; \nResponse Code : $responseCode\n")
            } else {
                responseCode
            }
            val data = inputStream.bufferedReader().readText()
            //println("Response: $data")
            Gson().fromJson(data, OpenWeatherResponse::class.java)
        }
    }
}