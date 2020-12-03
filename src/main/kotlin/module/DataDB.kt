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
    val id: Int = 0,
    val coordinates: Int,
    val value: String,
    val date: Date
)

/**
 * Данные о координатах для БД
 *
 * @property id ID
 * @property lat Координаты lat
 * @property lng координаты lng
 */
data class Coordinates(
    val id: Int = 0,
    val lat: String,
    val lng: String
)
