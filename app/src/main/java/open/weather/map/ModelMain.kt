package open.weather.map

import java.io.Serializable

class ModelMain : Serializable {
    var timeNow: String? = null
    var descWeather: String? = null
    var currentTemp = 0.0
    var tempMax = 0.0
    var tempMin = 0.0
}