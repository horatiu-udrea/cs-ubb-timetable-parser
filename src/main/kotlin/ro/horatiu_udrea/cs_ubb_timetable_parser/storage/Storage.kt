package ro.horatiu_udrea.cs_ubb_timetable_parser.storage

import ro.horatiu_udrea.cs_ubb_timetable_parser.TimetableSet
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.Timetable
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.TimetableIndex

interface Storage {

    /**
     * Save timetable index
     */
    suspend fun saveTimetableIndex(timetableIndex: TimetableIndex)

    /**
     * Save timetable
     */
    suspend fun saveTimetable(
        timetableSet: TimetableSet,
        specialisationName: String,
        groupName: String,
        link: String,
        timetable: Timetable
    )
}