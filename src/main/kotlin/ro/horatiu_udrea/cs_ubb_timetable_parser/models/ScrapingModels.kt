package ro.horatiu_udrea.cs_ubb_timetable_parser.models

import kotlinx.serialization.Serializable

@Serializable
data class ScrapedIndex(
    val bachelors: List<ScrapedTableItem>,
    val masters: List<ScrapedTableItem>
)

@Serializable
data class ScrapedTableItem(
    val name: String,
    val scrapedYears: List<ScrapedYear>
)

@Serializable
data class ScrapedYear(
    val name: String,
    val link: String
)

@Serializable
data class ScrapedTimetable(
    val timetables: List<ScrapedGroupTimetable>
)

@Serializable
data class ScrapedGroupTimetable(
    val name: String,
    val classes: List<ScrapedClassInfo>
)

@Serializable
data class ScrapedClassInfo(
    val day: String,
    val time: String,
    val frequency: String,
    val room: String,
    val roomLink: String,
    val formation: String,
    val type: String,
    val subject: String,
    val subjectLink: String,
    val teacher: String,
    val teacherLink: String
)