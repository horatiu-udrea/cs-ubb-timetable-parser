package ro.horatiu_udrea.cs_ubb_timetable_parser

import io.ktor.client.*
import kotlinx.datetime.TimeZone
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.Creator
import java.nio.file.Path

interface ProgramConfiguration {
    /**
     * Timezone used for time retrieval
     */
    val timezone: TimeZone

    /**
     * The creator of the timetables
     */
    val creator: Creator

    /**
     * Generate the description of the timetables
     * @param groupName The name of the group that the timetable is for
     */
    fun getTimetableDescription(groupName: String): String

    /**
     * Create the directories for storing the index and the timetables
     */
    fun createTimetableSetPath(timetableSet: TimetableSet)

    /**
     * Generate the path where the index file should be stored
     */
    fun getIndexPath(): Path

    /**
     * Generate the path where the markdown index file should be stored
     */
    fun getMarkdownIndexPath(): Path

    /**
     * Generate the path where the markdown timetable file should be stored for
     * the specified group
     */
    fun getMarkdownTimetablePath(timetableSet: TimetableSet, groupName: String): Path

    /**
     * Generate the URL for the index
     */
    fun getTimetableSetIndexUrl(timetableSet: TimetableSet): String

    /**
     * Get timetable URL having the timetable set and a link
     */
    fun getTimetableUrl(timetableSet: TimetableSet, link: String): String

    /**
     * Generate the path where the timetable file should be stored for
     * the specified group
     */
    fun getTimetablePath(timetableSet: TimetableSet, groupName: String): Path

    /**
     * Attempt to correct common spelling mistakes in the parsed data
     */
    fun String.correctSpelling(): String

    /**
     * Create the [HttpClient] that will be used to make network calls
     */
    fun createHttpClient(): HttpClient
}