package ro.horatiu_udrea.cs_ubb_timetable_parser.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class Timetable(
    val date: LocalDate? = null,
    val description: String = "",
    val original: String = "",
    val creator: Creator? = null,
    val days: List<Day>
)

@Serializable
data class Day(
    val name: String,
    val lessons: List<Lesson>
)

@Serializable
data class Creator(
    val name: String,
    val email: String = "",
    val website: String = "",
)

@Serializable
data class Lesson(
    val name: String,
    val type: String = "",
    val room: String = "",
    val teacher: String = "",
    val formation: String = "",
    val start: LocalTime,
    val end: LocalTime,
    val week: Int? = null
)