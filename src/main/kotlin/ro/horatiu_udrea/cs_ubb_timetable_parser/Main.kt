package ro.horatiu_udrea.cs_ubb_timetable_parser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.todayIn
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.TimetableIndex
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.TimetableSetIndex
import ro.horatiu_udrea.cs_ubb_timetable_parser.parser.LocalTimetableParser
import ro.horatiu_udrea.cs_ubb_timetable_parser.parser.TimetableParser
import ro.horatiu_udrea.cs_ubb_timetable_parser.scraper.TimetableScraper
import ro.horatiu_udrea.cs_ubb_timetable_parser.scraper.WebsiteTimetableScraper
import ro.horatiu_udrea.cs_ubb_timetable_parser.storage.MarkdownStorage
import ro.horatiu_udrea.cs_ubb_timetable_parser.storage.YamlStorage
import kotlin.time.measureTime

const val SEMESTER_1 = 1
const val SEMESTER_2 = 2

fun main(vararg args: String) = with(StaticProgramConfiguration(args)) {
    val currentYear = Clock.System.todayIn(timezone).year
    val lastYear = currentYear - 1
    val timetableSets = listOf(
        TimetableSet(currentYear, SEMESTER_2),
        TimetableSet(currentYear, SEMESTER_1),
        TimetableSet(lastYear, SEMESTER_2),
        TimetableSet(lastYear, SEMESTER_1)
    )

    createHttpClient().use { httpClient ->
        val scraper = WebsiteTimetableScraper(httpClient)
        val storage = setOf(
            YamlStorage(),
            MarkdownStorage()
        )
        val parser = LocalTimetableParser(scraper, storage)

        measureTime {
            runBlocking(Dispatchers.IO) {
                // Download all timetables in parallel
                coroutineScope {
                    val availableTimetables = timetableSets
                        .map { timetableSet -> async { downloadTimetableSet(timetableSet, scraper, parser) } }
                        .mapNotNull { deferred -> deferred.await() }

                    val timetableIndex = TimetableIndex(availableTimetables)
                    storage.forEach { it.saveTimetableIndex(timetableIndex) }
                }
            }
        }.let { println("Completed in ${it.inWholeMilliseconds} milliseconds") }
    }
}

context(ProgramConfiguration)
suspend fun downloadTimetableSet(
    timetableSet: TimetableSet,
    scraper: TimetableScraper,
    parser: TimetableParser
): TimetableSetIndex? {
    val scrapedIndex = scraper.scrapeTimetableIndex(timetableSet) ?: return null

    val (year, semester) = timetableSet
    val timetableIndexUrl = getTimetableSetIndexUrl(timetableSet)
    println("Downloading $year-$semester from $timetableIndexUrl")

    createTimetableSetPath(timetableSet)
    return parser.parseTimetable(timetableSet, scrapedIndex)
}