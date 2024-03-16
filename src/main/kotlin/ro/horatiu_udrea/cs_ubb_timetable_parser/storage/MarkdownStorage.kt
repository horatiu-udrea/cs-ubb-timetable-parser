package ro.horatiu_udrea.cs_ubb_timetable_parser.storage

import kotlinx.datetime.Clock
import kotlinx.datetime.todayIn
import ro.horatiu_udrea.cs_ubb_timetable_parser.ProgramConfiguration
import ro.horatiu_udrea.cs_ubb_timetable_parser.TimetableSet
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.StudyType
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.Timetable
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.TimetableIndex
import java.io.PrintWriter
import kotlin.io.path.outputStream
import kotlin.io.path.relativeTo

context(ProgramConfiguration)
class MarkdownStorage: Storage {

    override suspend fun saveTimetableIndex(timetableIndex: TimetableIndex) {
        val markdownIndexPath = getMarkdownIndexPath()
        PrintWriter(markdownIndexPath.outputStream()).use { writer ->
            val date = Clock.System.todayIn(timezone)
            val fetchDate = "${date.dayOfMonth}.${date.monthNumber.toString().padStart(2, '0')}.${date.year}"
            writer.println("# Orar Mate-Info UBB • Ultima actualizare: $fetchDate")
            timetableIndex.timetableSetIndices.forEach { (year, semester, timetableSetIndices) ->
                writer.println("## Anul $year-${year + 1} • Semestrul $semester")
                timetableSetIndices.forEach { (studyType, year, specialisations) ->
                    writer.println("<details>")
                    writer.println()
                    writer.print("<summary>")
                    when (studyType) {
                        StudyType.BACHELORS -> writer.print("Licență")
                        StudyType.MASTERS -> writer.print("Master")
                    }
                    writer.println(" • Anul $year</summary>")
                    writer.println()
                    specialisations.forEach { (name, groups) ->
                        writer.print("$name: ")
                        val groupLinks = groups.joinToString { group ->
                            val path = getMarkdownTimetablePath(TimetableSet(year, semester), group)
                                .relativeTo(markdownIndexPath.parent)
                            "[$group]($path)"
                        }
                        writer.println("$groupLinks  ")
                    }
                    writer.println("</details>")
                    writer.println()
                }
            }
        }
    }

    override suspend fun saveTimetable(
        timetableSet: TimetableSet,
        specialisationName: String,
        groupName: String,
        link: String,
        timetable: Timetable
    ) {
        val markdownTimetablePath = getMarkdownTimetablePath(timetableSet, groupName)
        val yamlTimetablePath = getTimetablePath(timetableSet, groupName).relativeTo(markdownTimetablePath.parent)
        val (year, semester) = timetableSet
        PrintWriter(markdownTimetablePath.outputStream()).use { writer ->
            writer.println("# Orarul grupei $groupName • $specialisationName")
            val date = Clock.System.todayIn(timezone)
            val fetchDate = "${date.dayOfMonth}.${date.monthNumber.toString().padStart(2, '0')}.${date.year}"
            writer.println("###### Anul $year-${year + 1} • Semestrul $semester • Ultima actualizare: $fetchDate")
            val timetableUrl = getTimetableUrl(timetableSet, link)
            writer.println("###### [Original]($timetableUrl) • [Format YAML]($yamlTimetablePath)")
            writer.println()

            writer.println("|Ziua|Ora|Săpt|Nume|Tip|Sală|Profesor|Formație|")
            writer.println("|:--:|---|:--:|----|:-:|:--:|--------|:------:|")
            timetable.days.forEach { (dayName, lessons) ->
                lessons.forEach { lesson ->
                    writer.println(
                        "|$dayName|${lesson.start}-${lesson.end}|${lesson.week ?: ""}|" +
                                "${lesson.name}|${lesson.type}|${lesson.room}|${lesson.teacher}|${lesson.formation}|"
                    )
                }
            }
        }
    }
}