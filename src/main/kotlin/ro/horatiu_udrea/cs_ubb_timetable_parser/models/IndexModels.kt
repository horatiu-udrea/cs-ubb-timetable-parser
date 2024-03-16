package ro.horatiu_udrea.cs_ubb_timetable_parser.models

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class TimetableIndex(
    val timetableSetIndices: List<TimetableSetIndex>
)

@Serializable
data class TimetableSetIndex(
    val year: Int,
    val semester: Int,
    val entries: List<Entry>
)

@Serializable
data class Entry(
    val studyType: StudyType,
    val year: Int,
    val specialisations: List<Specialisation>
)

@Serializable
data class Specialisation(
    val name: String,
    val groups: List<String>
)

@Serializable
enum class StudyType {
    BACHELORS,
    MASTERS
}