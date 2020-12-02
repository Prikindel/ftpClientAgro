package module

import java.sql.Date

/**
 * Данные о передаваемых характеристиках
 *
 * @property id ID
 * @property coordinates ID координаты
 * @property value значение
 * @property date дата сбора данных
 */
data class Data(
    val id: Int,
    val coordinates: Int,
    val value: String,
    val date: Date
)

/**
 * Данные о координатах для БД
 *
 * @property id ID
 * @property latlng Координаты (lat, lng)
 */
data class Coordinates(
    val id: Int,
    val latlng: String
)
