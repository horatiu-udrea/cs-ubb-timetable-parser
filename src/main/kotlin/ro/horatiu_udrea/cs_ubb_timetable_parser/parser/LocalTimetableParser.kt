package ro.horatiu_udrea.cs_ubb_timetable_parser.parser

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.todayIn
import ro.horatiu_udrea.cs_ubb_timetable_parser.ProgramConfiguration
import ro.horatiu_udrea.cs_ubb_timetable_parser.TimetableSet
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.*
import ro.horatiu_udrea.cs_ubb_timetable_parser.scraper.TimetableScraper
import ro.horatiu_udrea.cs_ubb_timetable_parser.storage.Storage

context(ProgramConfiguration)
class LocalTimetableParser(private val timetableScraper: TimetableScraper, private val storage: Set<Storage>) :
    TimetableParser {

    override suspend fun parseTimetable(timetableSet: TimetableSet, scrapedIndex: ScrapedIndex): TimetableSetIndex {
        // Parse bachelor's degree timetables
        val bachelorEntries = parseTimetableItems(timetableSet, scrapedIndex.bachelors, StudyType.BACHELORS)

        // Parse master's degree timetables
        val mastersEntries = parseTimetableItems(timetableSet, scrapedIndex.masters, StudyType.MASTERS)

        val (year, semester) = timetableSet
        return TimetableSetIndex(year, semester, bachelorEntries + mastersEntries)
    }

    private suspend fun parseTimetableItems(
        timetableSet: TimetableSet,
        timetableItems: List<ScrapedTableItem>,
        studyType: StudyType
    ): List<Entry> = coroutineScope {
        timetableItems
            .flatMap { timetableItem ->
                // Associate each year with the corresponding specialisations
                timetableItem.scrapedYears.map { (name, link) ->
                    val scrapedYear = name.split(" ").last().toInt()
                    val specialisationName = timetableItem.name.correctSpelling()

                    val scrapedTimetable = timetableScraper.scrapeTimetable(timetableSet, link)
                    val groups = scrapedTimetable.timetables.map { groupTimetable ->
                        groupTimetable.name.removePrefix("Grupa ").replace('/', '-').also { groupName ->
                            // Save the group timetable asynchronously
                            launch {
                                saveGroupTimetable(
                                    timetableSet,
                                    specialisationName,
                                    link,
                                    groupName,
                                    groupTimetable
                                )
                            }
                        }
                    }
                    scrapedYear to Specialisation(specialisationName, groups)
                }
            }
            .toMultiMap()
            .map { (year, specialisationList) -> Entry(studyType, year, specialisationList) }
    }

    private suspend fun saveGroupTimetable(
        timetableSet: TimetableSet,
        specialisationName: String,
        link: String,
        groupName: String,
        groupTimetable: ScrapedGroupTimetable
    ) {
        val timetable = Timetable(
            date = Clock.System.todayIn(timezone),
            description = getTimetableDescription(groupName),
            original = getTimetableUrl(timetableSet, link),
            creator = creator,
            days = groupTimetable.classes.groupBy(
                keySelector = { it.day },
                valueTransform = {
                    val (start, end) = it.time.split('-')
                    Lesson(
                        name = it.subject.correctSpelling(),
                        type = it.type,
                        room = it.room,
                        teacher = it.teacher,
                        formation = it.formation,
                        start = LocalTime(start.toInt(), 0),
                        end = LocalTime(end.toInt(), 0),
                        week = it.frequency.removePrefix("sapt. ").toIntOrNull()
                    )
                }
            ).map { (day, lessonList) -> Day(day.correctSpelling(), lessonList) }
        )
        storage.forEach { it.saveTimetable(timetableSet, specialisationName, groupName, link, timetable) }
    }

    private fun <K, V> List<Pair<K, V>>.toMultiMap(): Map<K, List<V>> =
        fold(mutableMapOf<K, MutableList<V>>()) { map, (key, value) ->
            val list = map.getOrPut(key) { mutableListOf() }
            list.add(value)
            map
        }
}