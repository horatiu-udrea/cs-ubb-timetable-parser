package ro.horatiu_udrea.cs_ubb_timetable_parser

import io.ktor.client.*
import kotlinx.datetime.TimeZone
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.Creator
import java.net.URI
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.div

class StaticProgramConfiguration(@Suppress("UNUSED_PARAMETER") args: Array<out String>) : ProgramConfiguration {

    override val timezone = TimeZone.of("Europe/Bucharest")

    override val creator = Creator(
        name = "Studenții CS UBB",
        website = "https://github.com/horatiu-udrea/cs-ubb-timetable-parser"
    )

    override fun getTimetableDescription(groupName: String): String =
        "Orar descărcat automat prin Github Actions pentru grupa $groupName"

    private val timetablesPath = Path("timetables")

    private fun getTimetableCollectionPath(timetableSet: TimetableSet): Path {
        val (year, semester) = timetableSet

        return timetablesPath / "$year-$semester"
    }

    override fun createTimetableSetPath(timetableSet: TimetableSet) {
        getTimetableCollectionPath(timetableSet).createDirectories()
    }

    override fun getIndexPath(): Path =
        timetablesPath / "index.yaml"

    override fun getMarkdownIndexPath(): Path =
        timetablesPath / "index.md"

    override fun getMarkdownTimetablePath(timetableSet: TimetableSet, groupName: String): Path =
        getTimetableCollectionPath(timetableSet) / "$groupName.md"

    override fun getTimetablePath(timetableSet: TimetableSet, groupName: String) =
        getTimetableCollectionPath(timetableSet) / "$groupName.yaml"

    override fun getTimetableSetIndexUrl(timetableSet: TimetableSet): String {
        val (year, semester) = timetableSet

        return "https://www.cs.ubbcluj.ro/files/orar/$year-$semester/tabelar/"
    }

    override fun getTimetableUrl(timetableSet: TimetableSet, link: String): String =
        URI(getTimetableSetIndexUrl(timetableSet)).resolve(link).toString()

    override fun String.correctSpelling(): String =
        split(' ').joinToString(separator = " ") {
            when (it) {
                "Marti" -> "Marți"
                "informatica" -> "informatică"
                "Informatica" -> "Informatică"
                "matematica" -> "matematică"
                "Matematica" -> "Matematică"
                "romana" -> "română"
                "engleza" -> "engleză"
                "germana" -> "germană"
                "maghiara" -> "maghiară"
                "Informatiei" -> "Informației"
                "Inteligenta" -> "Inteligență"
                "Artificială" -> "Artificială"
                "in" -> "în"
                "si" -> "și"
                "Computationala" -> "Computațională"
                "Aplicata" -> "Aplicată"
                "inalta" -> "înaltă"
                "performanta" -> "performanță"
                "Stiinta" -> "Știința"
                "Bioinformatica" -> "Bioinformatică"
                "educationala" -> "educațională"
                "disertatie" -> "diserație"
                "aplicata" -> "aplicată"
                "orientata" -> "orientată"
                "diferential" -> "diferențial"
                "baza" -> "bază"
                "inteligenta" -> "inteligență"
                "artificiala" -> "artificială"
                "lucrarii" -> "lucrării"
                "licenta" -> "licență"
                "Etica" -> "Etică"
                "academica" -> "academică"
                "(in" -> "(în"
                "informatica)" -> "informatică)"
                "aplicatii" -> "aplicații"
                "aplicatiilor" -> "aplicațiilor"
                "climatica" -> "climatică"
                "virtuala" -> "virtuală"
                "geometrica" -> "geometrică"
                "functiilor" -> "funcțiilor"
                "cibernetica" -> "cibernetică"
                "retelelor" -> "rețelelor"
                "securitatii" -> "securității"
                "inovatiei" -> "inovației"
                "Ecuatii" -> "Ecuații"
                "partiale" -> "parțiale"
                "publica" -> "publică"
                "functionala" -> "funcțională"
                "asistata" -> "asistată"
                "observativa" -> "observativă"
                "Invatarea" -> "Învățarea"
                "programarii" -> "programării"
                else -> it
            }
        }

    override fun createHttpClient(): HttpClient = HttpClient()
}