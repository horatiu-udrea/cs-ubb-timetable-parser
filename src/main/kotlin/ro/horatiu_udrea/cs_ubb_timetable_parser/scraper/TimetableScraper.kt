package ro.horatiu_udrea.cs_ubb_timetable_parser.scraper

import ro.horatiu_udrea.cs_ubb_timetable_parser.TimetableSet
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.ScrapedIndex
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.ScrapedTimetable

interface TimetableScraper {
    /**
     * Scrapes the timetable index
     */
    suspend fun scrapeTimetableIndex(timetableSet: TimetableSet): ScrapedIndex?

    /**
     * Scrapes a timetable with all the groups
     */
    suspend fun scrapeTimetable(timetableSet: TimetableSet, link: String): ScrapedTimetable

}