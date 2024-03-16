package ro.horatiu_udrea.cs_ubb_timetable_parser.storage

import com.charleskorn.kaml.SingleLineStringStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.encodeToStream
import ro.horatiu_udrea.cs_ubb_timetable_parser.ProgramConfiguration
import ro.horatiu_udrea.cs_ubb_timetable_parser.TimetableSet
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.Timetable
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.TimetableIndex
import kotlin.io.path.outputStream

context(ProgramConfiguration)
class YamlStorage: Storage {

    private val serializer = Yaml(
        configuration = YamlConfiguration(
            singleLineStringStyle = SingleLineStringStyle.Plain,
            encodeDefaults = false
        )
    )

    override suspend fun saveTimetableIndex(timetableIndex: TimetableIndex) {
        getIndexPath().outputStream().use { stream ->
            serializer.encodeToStream(timetableIndex, stream)
        }
    }

    override suspend fun saveTimetable(
        timetableSet: TimetableSet,
        specialisationName: String,
        groupName: String,
        link: String,
        timetable: Timetable
    ) {
        getTimetablePath(timetableSet, groupName).outputStream().use { stream ->
            serializer.encodeToStream(timetable, stream)
        }
    }
}