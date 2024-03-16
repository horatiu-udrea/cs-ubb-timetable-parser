package ro.horatiu_udrea.cs_ubb_timetable_parser.parser

import ro.horatiu_udrea.cs_ubb_timetable_parser.TimetableSet
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.ScrapedIndex
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.TimetableSetIndex

interface TimetableParser {
    /**
     * Applies assumptions about the index and parses all timetables
     */
    suspend fun parseTimetable(timetableSet: TimetableSet, scrapedIndex: ScrapedIndex): TimetableSetIndex
}